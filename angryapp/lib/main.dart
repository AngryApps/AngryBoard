import 'package:angryapp/data/usecases/create_column/remote_create_column.dart';
import 'package:angryapp/data/usecases/usecases.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart';

import 'infra/http/http.dart';
import 'presentation/presenters/presenters.dart';
import 'ui/pages/pages.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: HomePage(
        presenter: StreamHomePresenter(
            createColumnUseCase: RemoteCreateColumn(
              httpClient: HttpAdapter(Client()),
              url: "http://10.0.2.2:8080/api/v1/columns",
            ),
            listColumnsUseCase: RemoteListColumns(
              httpClient: HttpAdapter(Client()),
              url: "http://10.0.2.2:8080/api/v1/columns",
            )),
      ),
    );
  }
}
