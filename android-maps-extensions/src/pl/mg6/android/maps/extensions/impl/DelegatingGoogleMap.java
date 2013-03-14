package pl.mg6.android.maps.extensions.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mg6.android.maps.extensions.Circle;
import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.GroundOverlay;
import pl.mg6.android.maps.extensions.Marker;
import pl.mg6.android.maps.extensions.Polygon;
import pl.mg6.android.maps.extensions.Polyline;
import pl.mg6.android.maps.extensions.TileOverlay;
import android.location.Location;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

public class DelegatingGoogleMap implements GoogleMap {

	private com.google.android.gms.maps.GoogleMap real;

	private Map<com.google.android.gms.maps.model.Marker, Marker> markers;
	private Map<com.google.android.gms.maps.model.Polyline, Polyline> polylines;
	private Map<com.google.android.gms.maps.model.Polygon, Polygon> polygons;
	private Map<com.google.android.gms.maps.model.Circle, Circle> circles;
	private Map<com.google.android.gms.maps.model.GroundOverlay, GroundOverlay> groundOverlays;
	private Map<com.google.android.gms.maps.model.TileOverlay, TileOverlay> tileOverlays;

	public DelegatingGoogleMap(com.google.android.gms.maps.GoogleMap real) {
		this.real = real;
		this.markers = new HashMap<com.google.android.gms.maps.model.Marker, Marker>();
		this.polylines = new HashMap<com.google.android.gms.maps.model.Polyline, Polyline>();
		this.polygons = new HashMap<com.google.android.gms.maps.model.Polygon, Polygon>();
		this.circles = new HashMap<com.google.android.gms.maps.model.Circle, Circle>();
		this.groundOverlays = new HashMap<com.google.android.gms.maps.model.GroundOverlay, GroundOverlay>();
		this.tileOverlays = new HashMap<com.google.android.gms.maps.model.TileOverlay, TileOverlay>();
	}

	@Override
	public Circle addCircle(CircleOptions circleOptions) {
		com.google.android.gms.maps.model.Circle realCircle = real.addCircle(circleOptions);
		Circle circle = new DelegatingCircle(realCircle, this);
		circles.put(realCircle, circle);
		return circle;
	}

	@Override
	public GroundOverlay addGroundOverlay(GroundOverlayOptions groundOverlayOptions) {
		com.google.android.gms.maps.model.GroundOverlay realGroundOverlay = real.addGroundOverlay(groundOverlayOptions);
		GroundOverlay groundOverlay = new DelegatingGroundOverlay(realGroundOverlay, this);
		groundOverlays.put(realGroundOverlay, groundOverlay);
		return groundOverlay;
	}

	@Override
	public Marker addMarker(MarkerOptions markerOptions) {
		com.google.android.gms.maps.model.Marker realMarker = real.addMarker(markerOptions);
		Marker marker = new DelegatingMarker(realMarker, this);
		markers.put(realMarker, marker);
		return marker;
	}

	@Override
	public Polygon addPolygon(PolygonOptions polygonOptions) {
		com.google.android.gms.maps.model.Polygon realPolygon = real.addPolygon(polygonOptions);
		Polygon polygon = new DelegatingPolygon(realPolygon, this);
		polygons.put(realPolygon, polygon);
		return polygon;
	}

	@Override
	public Polyline addPolyline(PolylineOptions polylineOptions) {
		com.google.android.gms.maps.model.Polyline realPolyline = real.addPolyline(polylineOptions);
		Polyline polyline = new DelegatingPolyline(realPolyline, this);
		polylines.put(realPolyline, polyline);
		return polyline;
	}

	@Override
	public TileOverlay addTileOverlay(TileOverlayOptions tileOverlayOptions) {
		com.google.android.gms.maps.model.TileOverlay realTileOverlay = real.addTileOverlay(tileOverlayOptions);
		TileOverlay tileOverlay = new DelegatingTileOverlay(realTileOverlay, this);
		tileOverlays.put(realTileOverlay, tileOverlay);
		return tileOverlay;
	}

	@Override
	public void animateCamera(CameraUpdate cameraUpdate, CancelableCallback cancelableCallback) {
		real.animateCamera(cameraUpdate, cancelableCallback);
	}

	@Override
	public void animateCamera(CameraUpdate cameraUpdate, int time, CancelableCallback cancelableCallback) {
		real.animateCamera(cameraUpdate, time, cancelableCallback);
	}

	@Override
	public void animateCamera(CameraUpdate cameraUpdate) {
		real.animateCamera(cameraUpdate);
	}

	@Override
	public void clear() {
		real.clear();
		markers.clear();
		polylines.clear();
		polygons.clear();
		circles.clear();
		groundOverlays.clear();
		tileOverlays.clear();
	}

	@Override
	public CameraPosition getCameraPosition() {
		return real.getCameraPosition();
	}

	@Override
	public int getMapType() {
		return real.getMapType();
	}

	@Override
	public List<Circle> getCircles() {
		return new ArrayList<Circle>(circles.values());
	}

	@Override
	public List<GroundOverlay> getGroundOverlays() {
		return new ArrayList<GroundOverlay>(groundOverlays.values());
	}

	@Override
	public List<Marker> getMarkers() {
		return new ArrayList<Marker>(markers.values());
	}

	@Override
	public List<Polygon> getPolygons() {
		return new ArrayList<Polygon>(polygons.values());
	}

	@Override
	public List<Polyline> getPolylines() {
		return new ArrayList<Polyline>(polylines.values());
	}

	@Override
	public List<TileOverlay> getTileOverlays() {
		return new ArrayList<TileOverlay>(tileOverlays.values());
	}

	@Override
	public float getMaxZoomLevel() {
		return real.getMaxZoomLevel();
	}

	@Override
	public float getMinZoomLevel() {
		return real.getMinZoomLevel();
	}

	@Override
	public Location getMyLocation() {
		return real.getMyLocation();
	}

	@Override
	public Projection getProjection() {
		return real.getProjection();
	}

	@Override
	public UiSettings getUiSettings() {
		return real.getUiSettings();
	}

	@Override
	public boolean isIndoorEnabled() {
		return real.isIndoorEnabled();
	}

	@Override
	public boolean isMyLocationEnabled() {
		return real.isMyLocationEnabled();
	}

	@Override
	public boolean isTrafficEnabled() {
		return real.isTrafficEnabled();
	}

	@Override
	public void moveCamera(CameraUpdate cameraUpdate) {
		real.moveCamera(cameraUpdate);
	}

	@Override
	public boolean setIndoorEnabled(boolean indoorEnabled) {
		return real.setIndoorEnabled(indoorEnabled);
	}

	@Override
	public void setInfoWindowAdapter(final InfoWindowAdapter infoWindowAdapter) {
		com.google.android.gms.maps.GoogleMap.InfoWindowAdapter realInfoWindowAdapter = null;
		if (infoWindowAdapter != null) {
			realInfoWindowAdapter = new DelegatingInfoWindowAdapter(infoWindowAdapter);
		}
		real.setInfoWindowAdapter(realInfoWindowAdapter);
	}

	@Override
	public void setLocationSource(LocationSource locationSource) {
		real.setLocationSource(locationSource);
	}

	@Override
	public void setMapType(int mapType) {
		real.setMapType(mapType);
	}

	@Override
	public void setMyLocationEnabled(boolean myLocationEnabled) {
		real.setMyLocationEnabled(myLocationEnabled);
	}

	@Override
	public void setOnCameraChangeListener(OnCameraChangeListener onCameraChangeListener) {
		real.setOnCameraChangeListener(onCameraChangeListener);
	}

	@Override
	public void setOnInfoWindowClickListener(OnInfoWindowClickListener onInfoWindowClickListener) {
		com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener realOnInfoWindowClickListener = null;
		if (onInfoWindowClickListener != null) {
			realOnInfoWindowClickListener = new DelegatingOnInfoWindowClickListener(onInfoWindowClickListener);
		}
		real.setOnInfoWindowClickListener(realOnInfoWindowClickListener);
	}

	@Override
	public void setOnMapClickListener(OnMapClickListener onMapClickListener) {
		real.setOnMapClickListener(onMapClickListener);
	}

	@Override
	public void setOnMapLongClickListener(OnMapLongClickListener onMapLongClickListener) {
		real.setOnMapLongClickListener(onMapLongClickListener);
	}

	@Override
	public void setOnMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
		com.google.android.gms.maps.GoogleMap.OnMarkerClickListener realOnMarkerClickListener = null;
		if (onMarkerClickListener != null) {
			realOnMarkerClickListener = new DelegatingOnMarkerClickListener(onMarkerClickListener);
		}
		real.setOnMarkerClickListener(realOnMarkerClickListener);
	}

	@Override
	public void setOnMarkerDragListener(OnMarkerDragListener onMarkerDragListener) {
		com.google.android.gms.maps.GoogleMap.OnMarkerDragListener realOnMarkerDragListener = null;
		if (onMarkerDragListener != null) {
			realOnMarkerDragListener = new DelegatingOnMarkerDragListener(onMarkerDragListener);
		}
		real.setOnMarkerDragListener(realOnMarkerDragListener);
	}

	@Override
	public void setOnMyLocationChangeListener(OnMyLocationChangeListener onMyLocationChangeListener) {
		real.setOnMyLocationChangeListener(onMyLocationChangeListener);
	}

	@Override
	public void setTrafficEnabled(boolean trafficEnabled) {
		real.setTrafficEnabled(trafficEnabled);
	}

	@Override
	public void stopAnimation() {
		real.stopAnimation();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof DelegatingGoogleMap)) {
			return false;
		}
		DelegatingGoogleMap other = (DelegatingGoogleMap) o;
		return real.equals(other.real);
	}

	@Override
	public int hashCode() {
		return real.hashCode();
	}

	@Override
	public String toString() {
		return real.toString();
	}

	void remove(com.google.android.gms.maps.model.Marker marker) {
		markers.remove(marker);
	}

	void remove(com.google.android.gms.maps.model.Polyline polyline) {
		polylines.remove(polyline);
	}

	void remove(com.google.android.gms.maps.model.Polygon polygon) {
		polygons.remove(polygon);
	}

	void remove(com.google.android.gms.maps.model.Circle circle) {
		circles.remove(circle);
	}

	void remove(com.google.android.gms.maps.model.GroundOverlay groundOverlay) {
		groundOverlays.remove(groundOverlay);
	}

	void remove(com.google.android.gms.maps.model.TileOverlay tileOverlay) {
		tileOverlays.remove(tileOverlay);
	}

	private class DelegatingInfoWindowAdapter implements com.google.android.gms.maps.GoogleMap.InfoWindowAdapter {

		private InfoWindowAdapter infoWindowAdapter;

		private DelegatingInfoWindowAdapter(InfoWindowAdapter infoWindowAdapter) {
			this.infoWindowAdapter = infoWindowAdapter;
		}

		@Override
		public View getInfoWindow(com.google.android.gms.maps.model.Marker marker) {
			return infoWindowAdapter.getInfoWindow(map(marker));
		}

		@Override
		public View getInfoContents(com.google.android.gms.maps.model.Marker marker) {
			return infoWindowAdapter.getInfoContents(map(marker));
		}
	}

	private class DelegatingOnInfoWindowClickListener implements com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener {

		private OnInfoWindowClickListener onInfoWindowClickListener;

		private DelegatingOnInfoWindowClickListener(OnInfoWindowClickListener onInfoWindowClickListener) {
			this.onInfoWindowClickListener = onInfoWindowClickListener;
		}

		@Override
		public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {
			onInfoWindowClickListener.onInfoWindowClick(map(marker));
		}
	}

	private class DelegatingOnMarkerClickListener implements com.google.android.gms.maps.GoogleMap.OnMarkerClickListener {

		private OnMarkerClickListener onMarkerClickListener;

		private DelegatingOnMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
			this.onMarkerClickListener = onMarkerClickListener;
		}

		@Override
		public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
			return onMarkerClickListener.onMarkerClick(map(marker));
		}
	}

	private class DelegatingOnMarkerDragListener implements com.google.android.gms.maps.GoogleMap.OnMarkerDragListener {

		private OnMarkerDragListener onMarkerDragListener;

		private DelegatingOnMarkerDragListener(OnMarkerDragListener onMarkerDragListener) {
			this.onMarkerDragListener = onMarkerDragListener;
		}

		@Override
		public void onMarkerDragStart(com.google.android.gms.maps.model.Marker marker) {
			onMarkerDragListener.onMarkerDragStart(map(marker));
		}

		@Override
		public void onMarkerDrag(com.google.android.gms.maps.model.Marker marker) {
			onMarkerDragListener.onMarkerDrag(map(marker));
		}

		@Override
		public void onMarkerDragEnd(com.google.android.gms.maps.model.Marker marker) {
			onMarkerDragListener.onMarkerDragEnd(map(marker));
		}
	}

	private Marker map(com.google.android.gms.maps.model.Marker marker) {
		return markers.get(marker);
	}
}