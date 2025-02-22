import 'dart:convert';
import 'dart:developer';

import 'package:angryapp/data/http/http.dart';

import '../../domain/entities/ws/ws.dart';
import 'package:http/http.dart';

class HttpAdapter implements HttpClient {
  final Client client;

  HttpAdapter(this.client);

  @override
  Future<BaseWsResponse> post({
    required String url,
    Map? body,
    Map? headers,
  }) async {
    final defaultHeaders = headers?.cast<String, String>() ?? {}
      ..addAll({
        'content-type': 'application/json',
        'accept': 'application/json',
      });

    final jsonBody = body != null ? jsonEncode(body) : null;

    try {
      return _handleResponse(
        await client.post(
          Uri.parse(url),
          headers: defaultHeaders,
          body: jsonBody,
        ),
      );
    } catch (error) {
      log("Error: ${error.toString()}");
      throw (HttpError.serverError);
    }
  }

  BaseWsResponse _handleResponse(Response response) {
    if (response.statusCode == 200) {
      String decoded = utf8.decode(response.bodyBytes);
      return BaseWsResponse.fromJson(decoded);
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
