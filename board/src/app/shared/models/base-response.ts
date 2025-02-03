export interface BaseResponse<T> {
  success: boolean;
  message: string;
  metadata: unknown;
  data: T;
}
