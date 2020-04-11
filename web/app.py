from flask import Flask, render_template, request

from utils import api_request, params_dict

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def hello_world():
	if request.method == 'POST':
		print(request.form)
	response = api_request("current", params_dict(lat=39.160489, lon=-8.548239))
	return render_template('hello.html', **response)


if __name__ == '__main__':
	if app.config['DEBUG']:
		app.jinja_env.auto_reload = True
		app.config['TEMPLATES_AUTO_RELOAD'] = True
	app.run()
