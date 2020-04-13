import requests
from requests import Response


def api_request(service: str, params: dict) -> Response:
	return requests.get(
		f"http://localhost:8080/{service}",
		params=params
	)


def params_dict(lat: str, lon: str, hours: str = None) -> dict:
	params = {
		'lat': lat,
		'lon': lon
	}
	if hours:
		params['hours'] = hours

	return params


def pack_response(response: dict) -> dict:
	if 'airQuality' in response and response['airQuality']:
		response['airQuality'] = [response['airQuality']]
	else:
		response['airQuality'] = response['multipleAirQuality']
		del response['multipleAirQuality']
	return response
