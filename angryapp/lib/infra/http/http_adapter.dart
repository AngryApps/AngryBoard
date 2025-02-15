import 'dart:convert';

import 'package:angryapp/data/http/http.dart';
import 'package:http/http.dart';

class HttpAdapter implements HttpClient {
  final Client client;

  HttpAdapter(this.client);

  @override
  Future<dynamic> request({
    required String url,
    required String method,
    Map? body,
    Map? headers,
  }) async {
    final defaultHeaders = headers?.cast<String, String>() ?? {}
      ..addAll({
        'content-type': 'application/json',
        'accept': 'application/json',
      });

    final jsonBody = body != null ? jsonEncode(body) : null;
    var response = Response('', 500);

    try {
      if (method == 'POST') {
        response = await client.post(
          Uri.parse(url),
          headers: defaultHeaders,
          body: jsonBody,
        );
      } else if (method == 'GET') {
        response = await client.get(
          Uri.parse(url),
          headers: defaultHeaders,
        );
      } else if (method == 'PUT') {
        response = await client.put(
          Uri.parse(url),
          headers: defaultHeaders,
          body: jsonBody,
        );
      }
    } catch (error) {
      throw (HttpError.serverError);
    }
    return _handleResponse(response);
  }

  dynamic _handleResponse(Response response) {
    if (response.statusCode == 200) {
      return response.body.isEmpty ? null : jsonDecode(response.body);
    } else if (response.statusCode == 400) {
      throw (HttpError.badRequest);
    } else if (response.statusCode == 401) {
      throw (HttpError.unauthorized);
    } else if (response.statusCode == 404) {
      throw (HttpError.notFound);
    } else {
      throw (HttpError.serverError);
    }
  }
}
