package org.smu.blood.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import org.smu.blood.R
import org.smu.blood.databinding.ActivityMapBinding
import kotlin.properties.Delegates

class MapActivity : AppCompatActivity() , GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback{
    //바인딩
    private var mBinding: ActivityMapBinding? = null
    private val binding get() = mBinding!!

    //구글맵 관련 변수들
    private lateinit var mMap:GoogleMap
    private lateinit var mCameraPosition:CameraPosition

    //Places api의 엔트리 포인트(?)
    private lateinit var mGeoDataClient: GeoDataClient
    private lateinit var mPlaceDetectionClient: PlaceDetectionClient

    //위치 관련 변수(상수 참고)
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationPermissionGranted = false
    private var mLastKnownLocation: Location? = null

    //근처 장소(아마도)
    private lateinit var mLikelyPlaceNames: Array<String>
    private lateinit var mLikelyPlaceAddress: Array<String>
    private lateinit var mLikelyPlaceAttributions: Array<String>
    private lateinit var mLikelyPlaceLatLngs: Array<LatLng?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //위치랑 카메라 권한 상태인가?? 아니면 걍 포지션..?
        if(savedInstanceState != null){
            mLastKnownLocation = savedInstanceState.getParcelable<Location>(KEY_LOCATION)!!
            mCameraPosition = savedInstanceState.getParcelable<CameraPosition>(KEY_CAMERA_POSITION)!!
        }

        setContentView(R.layout.activity_map)

        mGeoDataClient = Places.getGeoDataClient(this, null)
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // 지도 빌드하기
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        if(mMap != null){
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.cameraPosition)
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation)
            super.onSaveInstanceState(outState, outPersistentState)
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return true
    }

    override fun onMyLocationClick(p0: Location) {
        
    }

    /**
     * Saves the state of the map when the activity is paused.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        Log.e(TAG, "onMapReady :")
        mMap = googleMap
        mMap.setInfoWindowAdapter(object : InfoWindowAdapter {
            override fun getInfoContents(p0: Marker): View? {
                //아마 여기에서 텍스트뷰 이런거 선언해야 하는 것 같은데 바인딩 쓸거라 이걸 사용할지 잘 모르겠음.
                return null
            }

            override fun getInfoWindow(p0: Marker): View? {
                return null
            }

        })
        getLocationPermission()
        getDeviceLocation()
        //showCurrentPlace()
        updateLocationUI()
    }


    //기기의 현재 위치 받아오고, 현위치를 지도에 띄우기
    @SuppressLint("MissingPermission")
    private fun getDeviceLocation(){
        //가장 최근의 위치를 바당올 수 있도록 함
        //구글맵에 간혹 위치가 안찍히는 장소가 있다고 하는데... 거기에 당첨이 될지..?
        try {
            if(mLocationPermissionGranted){
                var locationResult: Task<Location>  = mFusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this, object : OnCompleteListener<Location> {
                    override fun onComplete(@NonNull task: Task<Location>) {
                        if(task.isSuccessful){
                            //지도 카메라 위치를 내 위치로 땡겨오기
                            mLastKnownLocation = task?.result!!
                            Log.d("내 위치", mLastKnownLocation!!.latitude.toString()+", "+mLastKnownLocation!!.longitude)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
                                mLastKnownLocation!!.latitude, mLastKnownLocation!!.longitude),
                                DEFAULT_ZOOM.toFloat()
                            ))
                        } else{
                            Log.d(TAG, "현재 위치를 찾을 수 없어서 디폴트 씀")
                            Log.e(TAG, "exception: %s", task.exception)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                mDefaultLocation,
                                DEFAULT_ZOOM.toFloat()
                            ))
                            mMap.uiSettings.isMyLocationButtonEnabled = false
                        }
                    }
                })

            }
        } catch (e: SecurityException){
            Log.e("exception: %s", e.message!!)
        }
    }

    //사용자한테 기기 위치 권한 받아오기
    private fun getLocationPermission(){
        if(ContextCompat.checkSelfPermission(this.applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true

        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mLocationPermissionGranted = false
        if(requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true
            }
        }
        //updateLocationUI()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //현재 위치의 장소 보여주기?
    private fun showCurrentPlace(){
        if(mMap == null)
            return
        if(mLocationPermissionGranted){
            @SuppressWarnings("MissingPermission")
            val placeResult = mPlaceDetectionClient.getCurrentPlace(null)
            placeResult.addOnCompleteListener(
                object : OnCompleteListener<PlaceLikelihoodBufferResponse>{
                    override fun onComplete(task: Task<PlaceLikelihoodBufferResponse>) {
                       if(task.isSuccessful() && task.getResult() != null){
                           var likelyPlaces = task.getResult()
                           var count = 0
                           if(likelyPlaces.count < M_MAX_ENTRIES){
                               count = likelyPlaces.count
                           } else{
                               count = M_MAX_ENTRIES
                           }

                           var i = 0
                           mLikelyPlaceNames = arrayOf(arrayOf(count).toString())
                           mLikelyPlaceAddress = arrayOf(arrayOf(count).toString())
                           mLikelyPlaceAttributions = arrayOf(arrayOf(count).toString())
                           mLikelyPlaceLatLngs = arrayOfNulls(count)

                           for(placeLikelihood: PlaceLikelihood in likelyPlaces) {
                               mLikelyPlaceNames[i] = placeLikelihood.place.name.toString()
                               mLikelyPlaceAddress[i] = placeLikelihood.place.address.toString()
                               mLikelyPlaceAttributions[i] =
                                   placeLikelihood.place.attributions.toString()
                               mLikelyPlaceLatLngs[i] = placeLikelihood.place.latLng
                               i++
                               if (i > (count - 1))
                                   break
                           }

                           likelyPlaces.release()
                       } else
                           Log.e(TAG, "Exception: %s", task.exception)
                    }

                }
            )
        } else {
            Log.i(TAG, "사용자가 권한 거절함")
        }
        
        //사용자 디폴트 마커 찍기
        mMap.addMarker(MarkerOptions().title("지도 테스트").position(mDefaultLocation).snippet("이건 뭐임?"))
        //사용자한테 위치 권한 받기
        getLocationPermission()
    }

    /*private fun openPlacesDialog(){
        val listener =
            DialogInterface.OnClickListener { dialog, which -> // The "which" argument contains the position of the selected item.
                val markerLatLng: LatLng? = mLikelyPlaceLatLngs[which]
                var markerSnippet = mLikelyPlaceAddress[which]
                markerSnippet = """
                      $markerSnippet
                      ${mLikelyPlaceAttributions[which]}
                      """

                // Add a marker for the selected place, with an info window
                // showing information about that place.
                mMap.addMarker(
                    MarkerOptions()
                        .title(mLikelyPlaceNames[which])
                        .position(markerLatLng!!)
                        .snippet(markerSnippet)
                )

                // Position the map's camera at the location of the marker.
                mMap.moveCamera(
                    markerLatLng().let
                    markerLatLng.let {
                        CameraUpdateFactory.newLatLngZoom(
                            it,
                            DEFAULT_ZOOM.toFloat()
                        )
                    }
                )
            }
    }*/

    //사용자 권한 허용 여부에 따라 지도 설정 업데이트
    @SuppressLint("MissingPermission")
    private fun updateLocationUI(){
        if(mMap == null)
            return
        try{
            if(mLocationPermissionGranted){
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else{
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled=false
                mLastKnownLocation = null
                getLocationPermission()
            }
        }catch (e: SecurityException){
            Log.e("Exception: %s", e.message.toString())
        }
    }



    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

    companion object {
        //디폴트 좌표
        private val mDefaultLocation : LatLng = LatLng(37.56, 126.9)
        private val DEFAULT_ZOOM = 15
        private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        //액티비티 상태 저장 키
        private val KEY_CAMERA_POSITION = "camera_position"
        private val KEY_LOCATION = "location"

        //현재 장소 선택?(나중에 생략해도 될듯)
        private val M_MAX_ENTRIES = 5
    }

}