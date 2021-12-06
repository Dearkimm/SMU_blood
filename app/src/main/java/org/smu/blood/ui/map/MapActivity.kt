package org.smu.blood.ui.map

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import org.smu.blood.R
import org.smu.blood.databinding.ActivityMapBinding
import org.smu.blood.databinding.FragmentMapBinding

class MapActivity : AppCompatActivity() , GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback{
    //바인딩
    private var mBinding: ActivityMapBinding? = null
    private val binding get() = mBinding!!

    //권한
    private var permissionDenied = false
    //구글맵
    private lateinit var mMap:GoogleMap
    val DEFAULT_ZOOM_LEVEL = 17f
    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)
    val PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION)
    val REQUEST_PERMISSION_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            ?.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        //enableMyLocation()

        //테스트코드
        /*val markerOptions = MarkerOptions()
        markerOptions.position(CITY_HALL)
        markerOptions.title("서울")
        markerOptions.snippet("한국의 수도")
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, 15F))*/

    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}