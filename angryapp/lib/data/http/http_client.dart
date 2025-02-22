import 'package:angryapp/domain/entities/ws/ws.dart';

abstract class HttpClient {
  Future<BaseWsResponse> post({
    required String url,
    Map? body,
    Map? headers,
  });
}
