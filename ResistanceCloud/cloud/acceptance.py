import unittest

from api import ParseClient

class AcceptanceTest(unittest.TestCase):

  def test_should_call_hello_function(self):
    response = ParseClient().call_function('hello', { 'name' : 'Tiago' })
    assert response['result'] == 'Hello, Tiago'