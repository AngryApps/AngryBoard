import 'package:angryapp/domain/entities/entities.dart';

abstract class ListColumns {
  Future<List<ColumnEntity>> listColumns();
}
