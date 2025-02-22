import 'dart:async';

import 'package:angryapp/domain/entities/ws/create_column_params.dart';
import 'package:angryapp/domain/usecases/usecases.dart';
import 'package:angryapp/presentation/mixins/mixins.dart';
import 'package:angryapp/ui/pages/home/home.dart';

import '../../domain/entities/entities.dart';

class StreamHomePresenter with LoadingManager implements HomePresenter {
  final CreateColumn createColumnUseCase;

  final StreamController<String> _errorController =
      StreamController<String>.broadcast();

  final StreamController<ColumnEntity> _messageController =
      StreamController<ColumnEntity>.broadcast();

  StreamHomePresenter({required this.createColumnUseCase});

  @override
  Stream<String> get errorStream => _errorController.stream;

  @override
  Stream<ColumnEntity> get messageStream => _messageController.stream;

  @override
  Future<void> createColumn() async {
    isLoading = true;

    try {
      final column = await createColumnUseCase.create(CreateColumnParams(
        "Este é o título",
        "Esta é a descrição",
        0,
      ));

      await Future.delayed(Duration(seconds: 2));

      _messageController.add(column);
    } on Exception catch (error) {
      _errorController.add(error.toString());
    }

    isLoading = false;
  }
}
