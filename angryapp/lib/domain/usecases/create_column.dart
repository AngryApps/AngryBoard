import 'package:angryapp/domain/entities/entities.dart';
import 'package:angryapp/domain/entities/ws/create_column_params.dart';

abstract class CreateColumn {
  Future<ColumnEntity> create(CreateColumnParams params);
}
