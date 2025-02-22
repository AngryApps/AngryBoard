import '../../../domain/entities/entities.dart';

abstract class HomePresenter {
  Stream<String> get errorStream;
  Stream<ColumnEntity> get messageStream;
  Stream<List<ColumnEntity>> get listColumnsStream;
  Stream<bool> get isLoadingStream;

  Future<void> createColumn();
  Future<void> listColumns();
}
