import requests


def api_request(service: str, params: dict):
	return requests.get(
		f"http://localhost:8080/{service}",
		params=params
	).json()


def params_dict(lat: float, lon: float, hours: int = None):
	params = {
		'lat': lat,
		'lon': lon
	}
	if hours:
		params['hours'] = hours

	return params
