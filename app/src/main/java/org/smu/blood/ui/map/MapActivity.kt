package org.smu.blood.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import org.smu.blood.databinding.ActivityMapBinding
import org.smu.blood.R
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.maps.model.*
import org.smu.blood.ui.NavigationActivity


class MapActivity : AppCompatActivity() , GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback{
    //바인딩
    //private var mBinding: ActivityMapBinding? = null
    //private val binding get() = mBinding!!
//
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

    //병원좌표
    private var hospitalName = arrayOf("강남연세사랑병원", "강남힘찬병원", "강북힘찬병원", "강서힘찬병원", "강동성심병원"
    , "강남성심병원", "강남성심병원(신관)", "강동경희대학교병원", "건국대학교병원", "고려대학교의료원안암병원", "고려대학교구로병원"
    , "경희의료원", "목동힘찬병원", "서울아산병원", "신촌세브란스병원(연세의료원)", "서울대학교병원", "서울대학교어린이병원", "서울성모병원(가톨릭)"
    , "삼성서울병원(본원)", "삼성서울병원 암병원", "성애병원(신길)", "여의도성모병원(가톨릭)", "은평성모병원(가톨릭)"
    , "이대서울병원", "이대목동병원", "최원호병원", "척편한병원", "한강성심병원", "한양대학교구리병원", "혜민병원", "희명병원")
    private var hospitalLat = arrayOf(37.47668207240237, 37.50756717691139, 37.64563444869913, 37.55932815683345, 37.53623579906633
    , 37.492793432212835, 37.49114981693702, 37.55348577017648, 37.54081781433152, 37.58703944573309, 37.492219998055674
    , 37.59388695234703, 37.5249618918768, 37.527511354376344, 37.56234349074328, 37.579547092107504, 37.57934303139017, 37.5017816924235
    , 37.488948492747305, 37.48891117008248, 37.511966908482435, 37.51744157511374, 37.63384999074391, 37.5579008384366, 37.53638693421091
    , 37.60471552525695, 37.48269391615752, 37.523371835387806, 37.60120080369273, 37.535336333652666, 37.45567017986422)
    private var hospitalLon = arrayOf(126.9867632323917, 127.10992197655135, 127.03321859956753, 126.84060304240047, 127.13526462704681
    , 126.90881627476575, 126.90717045300246, 127.15764537131092, 127.07221647984983, 127.02654322858608, 126.88495345614182, 127.05133179978583
    , 126.87679967490885, 127.109096000744, 126.94086527961909, 126.99906034440808, 127.00050271306475, 127.00478170059976, 127.08764310917212
    , 127.08789197831823, 126.92238895573908, 126.93506125993063, 126.91655915777957, 126.83666540587318, 126.88642254991737, 126.92381679730522
    , 126.93010367335236, 126.91058215100577, 127.13236945818932, 127.08354151925478, 126.90059322938235)

    //헌혈의집 좌표(일단 일부만 ㅜㅜ)
    private var bloodCenterName = arrayOf("대한적십자사 서울중앙혈액원", "헌혈의집 서울역센터", "헌혈의집 신촌연대앞센터", "헌혈의집 신촌센터", "헌혈의집 연신내센터"
    , "헌혈의집 홍대센터 중앙혈액원", "헌혈의집 구로디지털단지역센터", "헌혈의집 서울대역센터", "헌혈의집 대방역센터", "헌혈의집 신도림테크노마트센터")
    private var bloodCenterLat = arrayOf(37.548306877256, 37.557804448222136, 37.557423861820006, 37.55580462135787, 37.619193348750024,
        37.55601572907209, 37.485228339956684, 37.47869303536205, 37.51287217850732, 37.50707682233656)
    private var bloodCenterLon = arrayOf(126.870757963193, 126.96943844782595, 126.93729104782592, 126.93781013988672, 126.92031837854357,
        126.92282714782516, 126.9015716784774, 126.95257816313065, 126.92599587849107, 126.8902613784882)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //위치랑 카메라 권한 상태인가?? 아니면 걍 포지션..?
        if(savedInstanceState != null){
            mLastKnownLocation = savedInstanceState.getParcelable<Location>(KEY_LOCATION)!!
            mCameraPosition = savedInstanceState.getParcelable<CameraPosition>(KEY_CAMERA_POSITION)!!
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_map)

        mGeoDataClient = Places.getGeoDataClient(this, null)
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // 지도 빌드하기
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //팝업 버튼 이벤트
        var btnGo = findViewById<Button>(R.id.btn_go)
        btnGo.setOnClickListener {
            mapState = 1
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }
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

        //리스너 달기
        mMap.setOnMarkerClickListener(this)
        //마커찍기(병원)
        for (i in hospitalName.indices) {
            // 1. 마커 옵션 설정 (만드는 과정)
            val makerOptions = MarkerOptions()
            makerOptions // LatLng에 대한 어레이를 만들음
                .position(LatLng(hospitalLat[i], hospitalLon[i]))
                .title(hospitalName[i]) // 타이틀.
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mk_red))

            // 2. 마커 생성 (마커를 나타냄)
            mMap.addMarker(makerOptions)
        }

        //마커찍기(헌혈의집)
        for (i in bloodCenterName.indices) {
            // 1. 마커 옵션 설정 (만드는 과정)
            val makerOptions = MarkerOptions()
            makerOptions // LatLng에 대한 어레이를 만들음
                .position(LatLng(bloodCenterLat[i], bloodCenterLon[i]))
                .title(bloodCenterName[i]) // 타이틀.
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mk_grey))

            // 2. 마커 생성 (마커를 나타냄)
            mMap.addMarker(makerOptions)
        }

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
            getLocationPermission()
            if(mLocationPermissionGranted){
                var locationResult: Task<Location>  = mFusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this, object : OnCompleteListener<Location> {
                    override fun onComplete(@NonNull task: Task<Location>) {
                        if(task.isSuccessful){
                            //지도 카메라 위치를 내 위치로 땡겨오기
                            mLastKnownLocation = task.result!!
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
        updateLocationUI()
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
        //mMap.addMarker(MarkerOptions().title("지도 테스트").position(mDefaultLocation).snippet("이건 뭐임?"))
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
        super.onDestroy()
    }

    companion object {
        //디폴트 좌표
        private val mDefaultLocation : LatLng = LatLng(37.56, 126.9)
        private val DEFAULT_ZOOM = 16
        private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        //액티비티 상태 저장 키
        private val KEY_CAMERA_POSITION = "camera_position"
        private val KEY_LOCATION = "location"

        //현재 장소 선택?(나중에 생략해도 될듯)
        private val M_MAX_ENTRIES = 5
        var mapState = 0
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        var popup = findViewById<ConstraintLayout>(R.id.con_popup)
        var hospital = findViewById<TextView>(R.id.tv_hospital)
        hospital.text = marker.title
        popup.visibility = VISIBLE

        return true

    }

}