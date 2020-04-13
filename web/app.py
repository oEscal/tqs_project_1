from flask import Flask, render_template, request

from utils import api_request, params_dict, pack_response

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def hello_world():
	response = {}
	if request.method == 'POST':
		type_request = request.form['type']
		response_raw = api_request(type_request, params_dict(
			lat=request.form['latitude'], lon=request.form['longitude'], hours=request.form['hours']
		))
		if response_raw.status_code != 200:
			response['success'] = False
			response['detail'] = "There was an error contacting the API"
		else:
			response = pack_response(response_raw.json())
			response['title'] = type_request[0].upper() + type_request[1:]
	return render_template('index.html', **response)


if __name__ == '__main__':
	if app.config['DEBUG']:
		app.jinja_env.auto_reload = True
		app.config['TEMPLATES_AUTO_RELOAD'] = True
	app.run()
