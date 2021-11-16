package org.smu.blood.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.smu.blood.R
import java.util.jar.Manifest

class MapFragment : Fragment(), OnMapReadyCallback {
    val DEFAULT_ZOOM_LEVEL = 17f
    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)
    var googleMap: GoogleMap? = null
    private lateinit var mapView: MapView
    //엄마가 보고 싶습니다 엉엉엉엉
    //권한 허용
    val PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION)
    val REQUEST_PERMISSION_CODE = 1
    private fun checkPermissions(): Boolean {
        for (permission in PERMISSIONS) {
            if (context?.let { ActivityCompat.checkSelfPermission(it, permission) } != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        initMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_map, container, false)

        when {
            checkPermissions() -> getMyLocation()?.let { CameraUpdateFactory.newLatLngZoom(it, DEFAULT_ZOOM_LEVEL) }?.let {
                googleMap?.moveCamera(
                    it
                )
            }
            else -> Toast.makeText(context, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
        }

        //구글맵 보여주기
        mapView = rootView.findViewById(R.id.mv_contactUs_gMap) as MapView
        mapView.onCreate(savedInstanceState)
        if (checkPermissions()) {
            initMap()
        } else {
            activity?.let { ActivityCompat.requestPermissions(it, PERMISSIONS, REQUEST_PERMISSION_CODE) }
        }
        mapView.getMapAsync(this) //씨발 개새끼야 왜 안떠 개새끼야 시발럼아 진짜

        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    //putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("MissingPermission")
    fun initMap() {
        mapView.getMapAsync {

            googleMap = it

            it.uiSettings.isMyLocationButtonEnabled = false

            when {
                checkPermissions() -> {
                    it.isMyLocationEnabled = true
                    getMyLocation()?.let { it1 ->
                        CameraUpdateFactory.newLatLngZoom(
                            it1, DEFAULT_ZOOM_LEVEL)
                    }?.let { it2 -> it.moveCamera(it2) }
                }
                else -> {
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng? {
//씨발롬아
        val locationProvider: String = LocationManager.GPS_PROVIDER
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
        if (lastKnownLocation != null) {
            return LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        }
        else return null
        //쌍놈새끼 진짜 개빡치게 하네 진짜 개새끼야 왜 자꾸 빨간줄 뜨고 지랄이냐 이 개새끼야 시벌
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
}