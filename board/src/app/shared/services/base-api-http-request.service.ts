import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BaseResponse } from '../models';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BaseApiHttpRequestService {
  httpClient = inject(HttpClient);

  // TODO create environment variable for base URL to replace hardcoded value
  private readonly baseUrl = 'http://localhost:8080/api/v1';

  private readonly headers = {
    'Content-Type': 'application/json',
  };

  get<T>(path: string, id?: string): Observable<BaseResponse<T>> {
    const basePath = id ? `${path}/${id}` : path;

    return this.httpClient
      .get<
        BaseResponse<T>
      >(`${this.baseUrl}/${basePath}`, { headers: this.headers })
      .pipe(catchError(this.handleError));
  }

  post<T>(path: string, body: unknown): Observable<BaseResponse<T>> {
    return this.httpClient
      .post<
        BaseResponse<T>
      >(`${this.baseUrl}/${path}`, body, { headers: this.headers })
      .pipe(catchError(this.handleError));
  }

  put<T>(path: string, body: unknown): Observable<BaseResponse<T>> {
    return this.httpClient
      .put<
        BaseResponse<T>
      >(`${this.baseUrl}/${path}`, body, { headers: this.headers })
      .pipe(catchError(this.handleError));
  }

  patch<T>(path: string, body: unknown): Observable<BaseResponse<T>> {
    return this.httpClient
      .patch<
        BaseResponse<T>
      >(`${this.baseUrl}/${path}`, body, { headers: this.headers })
      .pipe(catchError(this.handleError));
  }

  delete<T>(path: string, id: string): Observable<BaseResponse<T>> {
    return this.httpClient
      .delete<
        BaseResponse<T>
      >(`${this.baseUrl}/${path}/${id}`, { headers: this.headers })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    if (error.status === 404) {
      return throwError(() => new Error('Resource not found.'));
    }

    if (error.status === 401) {
      return throwError(() => new Error('Unauthorized.'));
    }

    if (error.status === 403) {
      return throwError(() => new Error('Forbidden.'));
    }

    if (error.status === 500) {
      return throwError(() => new Error('Internal server error.'));
    }

    if (error.status === 400) {
      return throwError(() => new Error('Bad request.'));
    }

    return throwError(
      () => new Error('An error occurred. Please try again later.'),
    );
  }
}
