import 'package:flutter/material.dart';

import '../components/components.dart';

mixin LoadingManager {
  void handleLoading(BuildContext context, Stream<bool?>? stream) {
    stream?.listen((isLoading) {
      if (isLoading == true) {
        if (context.mounted) showLoading(context);
      } else {
        if (context.mounted) hideLoading(context);
      }
    });
  }
}
