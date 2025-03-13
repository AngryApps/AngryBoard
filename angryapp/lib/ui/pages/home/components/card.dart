import 'package:angryapp/domain/entities/entities.dart';
import 'package:flutter/material.dart';

class CardComponent extends StatelessWidget {
  const CardComponent({
    super.key,
    required this.title,
    required this.subtitle,
    required this.onTap,
    required this.columns,
  });

  final String title;
  final String subtitle;
  final bool isProxy = false;
  final TextAlign alignment = TextAlign.start;
  final void Function() onTap;
  final List<ColumnEntity> columns;

  @override
  Widget build(BuildContext context) {
    return Card(
      shadowColor: Color.fromRGBO(121, 86, 72, 1),
      elevation: 4,
      child: ListTile(
        title: Text(
          title,
          textAlign: alignment,
          style: TextStyle(
            color: isProxy ? Colors.white : Color.fromRGBO(113, 95, 89, 1),
            fontWeight: FontWeight.bold,
          ),
        ),
        subtitle: Text(
          subtitle,
          style: TextStyle(
            color: isProxy ? Colors.white : Color.fromRGBO(113, 95, 89, 1),
          ),
        ),
        onTap: onTap,
      ),
    );
  }
}
