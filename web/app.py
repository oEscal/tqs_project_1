from flask import Flask, render_template, request

from utils import api_request, params_dict

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def hello_world():
	response = {}
	if request.method == 'POST':
		response = api_request(request.form['type'], params_dict(lat=request.form['latitude'], lon=request.form['longitude']))
	return render_template('hello.html', **response)


if __name__ == '__main__':
	if app.config['DEBUG']:
		app.jinja_env.auto_reload = True
		app.config['TEMPLATES_AUTO_RELOAD'] = True
	app.run()
