package com.nieelz.danielstoryapp.view.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nieelz.danielstoryapp.R
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, MapStoryAdapter.OnItemClickCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var currentMarker = mutableListOf<Marker>()
    private var currentStories = mutableListOf<ListStoryItem>()
    private lateinit var markerClickListener: GoogleMap.OnMarkerClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mapUiSetting()
        getMyLocation()

        intent.extras?.getParcelableArrayList<ListStoryItem>("EXTRA_MAP")?.let {
            binding.rvStoryMap.adapter = MapStoryAdapter(it.toList()).apply {
                setOnItemClickCallback(this@MapsActivity)
            }
            showStoriesLocation(it.take(20))
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }


    private fun showStoriesLocation(stories: List<ListStoryItem>) {
        stories.forEach {
            val coordinate = LatLng(it.lat ?: 0.0, it.lon ?: 0.0)

            val option = MarkerOptions().position(coordinate).title(it.name)

            val marker = mMap.addMarker(option)

            marker?.let { mMarker -> currentMarker.add(mMarker) }
            markerClickListener = GoogleMap.OnMarkerClickListener { m ->
                scrollListToPositionByName(m.title ?: "")
                false
            }
            mMap.setOnMarkerClickListener(markerClickListener)
        }
    }


    private fun scrollListToPositionByName(name: String) {
        val story = currentStories.find { it.name == name }
        val position = currentStories.indexOf(story)

        binding.rvStoryMap.apply {
            post { smoothScrollToPosition(position) }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun mapUiSetting() {
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
    }

    override fun onItemClicked(data: ListStoryItem) {
        // kalau di klik ngapain
    }


}