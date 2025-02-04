import { Card, CardWS } from '../../card/models/card';

export interface Column {
  id: string;
  title: string;
  description: string;
  position: number;
  createdAt: Date;
  updatedAt: Date;
  cards: Card[];
}

export interface ColumnResponse {
  id: string;
  title: string;
  description: string;
  position: number;
  created_at: Date;
  updated_at: Date;
  cards?: CardWS[];
}
