import 'package:angryapp/data/http/http.dart';
import 'package:angryapp/domain/entities/column_entity.dart';
import 'package:angryapp/domain/entities/ws/ws.dart';
import 'package:angryapp/domain/usecases/usecases.dart';

class RemoteCreateColumn implements CreateColumn {
  final HttpClient httpClient;
  final String url;

  RemoteCreateColumn({required this.httpClient, required this.url});

  @override
  Future<ColumnEntity> create(CreateColumnParams params) async {
    try {
      final body = CreateColumnRequest.fromDomain(params).toJson();
      final response = await httpClient.post(
        url: url,
        method: 'POST',
        body: body,
      );

      return ColumnEntity.fromMap(response.data);
    } on HttpError catch (error) {
      throw error.toString();
    }
  }
}

class CreateColumnRequest {
  final String title;
  final String description;
  final int position;

  CreateColumnRequest({
    required this.title,
    required this.description,
    required this.position,
  });

  Map<String, dynamic> toJson() {
    return {
      'title': title,
      'description': description,
      'position': position,
    };
  }

  factory CreateColumnRequest.fromDomain(CreateColumnParams params) =>
      CreateColumnRequest(
        title: params.title,
        description: params.description,
        position: params.position,
      );
}
