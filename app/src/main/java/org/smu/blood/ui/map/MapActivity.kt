package org.smu.blood.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
//import android.os.Bundle
import android.os.Looper
import android.telephony.SmsManager
import android.util.Log
//import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
//import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SettingsSlicesContract.KEY_LOCATION
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import org.smu.blood.R
import org.smu.blood.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() , GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback{
    //바인딩
    private var mBinding: ActivityMapBinding? = null
    private val binding get() = mBinding!!

    //구글맵 관련

    private val TAG = this.javaClass.simpleName
    private lateinit var mContext: Context
    private lateinit var mMap: GoogleMap
    private var currentMarker: Marker? = null

    private lateinit var mFusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var mCurrentLocatiion: Location? = null
    private var mCameraPosition: CameraPosition? = null
    private val mDefaultLocation = LatLng(37.56, 126.97)
    private var mLocationPermissionGranted = false

    //권한
    /*private var permissionDenied = false
    //구글맵
    private lateinit var mMap:GoogleMap
    val DEFAULT_ZOOM_LEVEL = 17f
    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)
    val PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION)
    val REQUEST_PERMISSION_CODE = 1*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*savedInstanceState?.let{
            mCurrentLocatiion = it.getParcelable(KEY_LOCATION)
            mCameraPosition = it.getParcelable(KEY_CAMERA_POSITION)
        }
        mContext = this@CurrentPlace
        mBinding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // 정확도를 최우선적으로 고려
            .setInterval(UPDATE_INTERVAL_MS.toLong()) // 위치가 Update 되는 주기
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong()) // 위치 획득후 업데이트되는 주기
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)*/

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Build the map.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
}

    override fun onMyLocationButtonClick(): Boolean {
        return true
    }

    override fun onMyLocationClick(p0: Location) {
        
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        mMap.let{
            outState.putParcelable(KEY_CAMERA_POSITION, it.cameraPosition)
            outState.putParcelable(KEY_LOCATION, mCurrentLocatiion)
            super.onSaveInstanceState(outState)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.e(TAG, "onMapReady :")
        mMap = googleMap
        //setDefaultLocation() // GPS를 찾지 못하는 장소에 있을 경우 지도의 초기 위치가 필요함.
        //locationPermission
        updateLocationUI()
        //deviceLocation
    }

    private fun updateLocationUI() {
        mMap?.let {
            try {
                if (mLocationPermissionGranted) {
                    it.isMyLocationEnabled = true
                    it.uiSettings.isMyLocationButtonEnabled = true
                } else {
                    it.isMyLocationEnabled = false
                    it.uiSettings.isMyLocationButtonEnabled = false
                    mCurrentLocatiion = null
                    locationPermission
                }
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message!!)
            }
        }
    }


    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}