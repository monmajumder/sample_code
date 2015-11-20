from urllib2 import Request
import json, http.client

class ParseClient:

  def __init__(self):
    self.connection = http.client.HTTPSConnection('api.parse.com', 443)
    self.connection.connect()

  def call_function(self, name, params):
    path = "/1/functions/{0}".format(name)
    print('\n' + path)
    self.connection.request('POST', path, json.dumps(params), {
       'X-Parse-Application-Id' : 'APP_ID',
       'X-Parse-REST-API-Key'   : 'API_KEY',
       'Content-Type'           : 'application/json'
      })
    response = self.connection.getresponse().read().decode()
    print(response)
    return json.loads(response)