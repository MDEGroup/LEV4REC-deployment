from RecSys_business import run_cross_fold

from flask import Flask
from flask import request
from flask import jsonify
import os
import json
app = Flask(__name__)
@app.route("/get_recommendation", methods=["POST"])
def recommend():    
	json_data = request.get_json()    
	context = json_data["context"]
	n_items = json_data["n_items"]
	run_cross_fold()
	try:
        
		message = {
			'status': 200,
			'message': 'OK',
			
		}
		return jsonify(message)
	except Exception:
		message = {
            'status': 500,
            'message': 'KO'
        }
	return message



if __name__ == '__main__':
	app.debug = True
	port = int(os.environ.get("PORT", ))
	app.run(host='', port=port)
