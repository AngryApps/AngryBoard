import 'dart:convert';

import 'package:angryapp/domain/entities/card_entity.dart';

class ColumnEntity {
  final String id;
  final String title;
  final String description;
  final DateTime createdAt;
  final DateTime updatedAt;
  final int position;
  final List<CardEntity> cards;

  ColumnEntity(
    this.id,
    this.title,
    this.description,
    this.createdAt,
    this.updatedAt,
    this.position,
    this.cards,
  );

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'createdAt': createdAt.millisecondsSinceEpoch,
      'updatedAt': updatedAt.millisecondsSinceEpoch,
      'position': position,
      'cards': cards.map((x) => x.toMap()).toList(),
    };
  }

  factory ColumnEntity.fromMap(Map<String, dynamic> map) {
    return ColumnEntity(
      map['id'] ?? '',
      map['title'] ?? '',
      map['description'] ?? '',
      DateTime.parse(map['createdAt']),
      DateTime.parse(map['updatedAt']),
      map['position']?.toInt() ?? 0,
      List<CardEntity>.from(map['cards']?.map((x) => CardEntity.fromMap(x))),
    );
  }

  String toJson() => json.encode(toMap());

  factory ColumnEntity.fromJson(String source) =>
      ColumnEntity.fromMap(json.decode(source));
}
