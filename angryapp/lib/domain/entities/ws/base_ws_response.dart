import 'dart:convert';

class BaseWsResponse<T> {
  final bool success;
  final String message;
  final Map<String, dynamic> metadata;
  final Map<String, dynamic> data;

  BaseWsResponse({
    required this.success,
    required this.message,
    required this.metadata,
    required this.data,
  });

  Map<String, dynamic> toMap() {
    return {
      'success': success,
      'message': message,
      'metadata': metadata,
      'data': data,
    };
  }

  factory BaseWsResponse.fromMap(Map<String, dynamic> map) {
    return BaseWsResponse<T>(
      success: map['success'] ?? false,
      message: map['message'] ?? '',
      metadata: Map<String, dynamic>.from(map['metadata'] ?? {}),
      data: Map<String, dynamic>.from(map['data'] ?? {}),
    );
  }

  String toJson() => json.encode(toMap());

  factory BaseWsResponse.fromJson(String source) =>
      BaseWsResponse.fromMap(json.decode(source));
}
