export interface Card {
  id: string;
  title: string;
  description: string;
  position: number;
  createdAt: Date;
  updatedAt: Date;
  columnId: string;
}

export interface CardResponse {
  id: string;
  title: string;
  description: string;
  position: number;
  created_at: Date;
  updated_at: Date;
  column_id: string;
}

export interface AddCardRequest {
  title: string;
  description?: string;
  columnId: string;
}

export interface EditCardRequest {
  id: string;
  title: string;
  description: string;
  position: number;
  columnId: string;
}

export interface MoveCardRequest {
  previousCardId?: string;
  nextCardId?: string;
  targetColumnId: string;
}
