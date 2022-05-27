
from flask import Flask

from flask import jsonify
import os

from flasgger import Swagger

from KNN_business import  run_cross_fold


app = Flask(__name__)
app.config['SWAGGER'] = {
    'title': 'LEV4REC-API',
    'uiversion': 3
}
swagger = Swagger(app)

@app.route("/recommend/<context>/<n_items>")
def recommend(context,n_items):
    """Lev4rec endpoint
    This is using docstrings for specifications.
    ---
    parameters:
      - name: context
        in: path
        type: string        
        required: true
        default: 771        
      
        
      - name: n_items
        in: path
        type: string    
        required: true
        default: 10    
    
    definitions:
      Context:
        type: object
        properties:
          context_files:
            type: array
            items:
              $ref: '#/definitions/Context'
      feedbackType:
        type: string
    responses:
      200:
        description: A list of user context
        schema:
          $ref: '#/definitions/Context'
        examples:
          context_files: projectdanube__xdi2
    """    

  
    
    n_items=int(n_items)
    res = run_cross_fold(n_items)
    
    try:
        
        message = {
            'status': 200,
            'message': 'OK',
            'results': res
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
    port = int(os.environ.get("PORT", 5000))
    app.run(host='0.0.0.0', port=port)
