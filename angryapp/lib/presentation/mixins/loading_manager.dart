import 'dart:async';

mixin LoadingManager {
  final _isLoadingController = StreamController<bool>.broadcast();

  Stream<bool> get isLoadingStream => _isLoadingController.stream;
  set isLoading(bool isLoading) {
    if (!_isLoadingController.isClosed) {
      _isLoadingController.add(isLoading);
    }
  }

  void closeLoadingManager() => _isLoadingController.close();
}
