export interface Card {
  id: string;
  title: string;
  description: string;
  createdAt: Date;
  updatedAt: Date;
  columnId: string;
  columnPosition: number;
  attachments: string[];
}

export interface CardResponse {
  id: string;
  title: string;
  description: string;
  created_at: Date;
  updated_at: Date;
  column_id: string;
  column_position: number;
  attachments: string[];
}

export interface AddCardRequest {
  title: string;
  description?: string;
  position: number;
  columnId: string;
}

export interface EditCardRequest {
  id: string;
  title: string;
  description: string;
  position: number;
  columnId: string;
}
