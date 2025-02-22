import 'package:flutter/material.dart';

late BuildContext loadingContext;

void showLoading(BuildContext context) {
  showDialog(
    context: context,
    barrierDismissible: false,
    builder: (dialogContext) {
      loadingContext = dialogContext;
      return SimpleDialog(
        children: [
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: const [
              CircularProgressIndicator(),
              SizedBox(height: 10),
              Text('Aguarde...', textAlign: TextAlign.center)
            ],
          )
        ],
      );
    },
  );
}

void hideLoading(BuildContext context) {
  if (Navigator.canPop(loadingContext)) {
    Navigator.of(loadingContext).pop();
  }
}
