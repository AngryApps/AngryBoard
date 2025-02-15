// import 'package:angryapp/data/http/http_client.dart';
// import 'package:angryapp/data/http/http_error.dart';
// import 'package:angryapp/domain/entities/column_entity.dart';
// import 'package:angryapp/domain/usecases/usecases.dart';

// import '../../../domain/helpers/helpers.dart';

// class RemoteCreateColumn implements CreateColumn {
//   final HttpClient httpClient;
//   final String url;

//   RemoteCreateColumn({required this.httpClient, required this.url});

//   @override
//   Future<ColumnEntity> createColumn(ColumnEntity column) async {
//     try {
//       final httpResponse = await httpClient.request(
//         url: url,
//         method: 'POST',
//         body: {},
//       );
//     } on HttpError catch (error) {
//       throw DomainError.unexpected;
//     }
//   }
// }
