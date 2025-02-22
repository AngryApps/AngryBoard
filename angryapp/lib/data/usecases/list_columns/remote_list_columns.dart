import 'package:angryapp/data/http/http.dart';
import 'package:angryapp/domain/entities/column_entity.dart';
import 'package:angryapp/domain/usecases/usecases.dart';

class RemoteListColumns implements ListColumns {
  final HttpClient httpClient;
  final String url;

  RemoteListColumns({required this.httpClient, required this.url});

  @override
  Future<List<ColumnEntity>> listColumns() async {
    try {
      final response = await httpClient.get(url: url);

      return (response.data as List)
          .map((json) => ColumnEntity.fromMap(json))
          .toList();
    } on HttpError catch (error) {
      throw error.toString();
    }
  }
}
