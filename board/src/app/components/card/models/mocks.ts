import { CardWS } from './card';

export const KANBAN_CARDS: CardWS[] = [
  // Backlog Cards
  {
    id: 'c1a2b3c4-d5e6-4f7a-8b9c-0d1e2f3a4b5c',
    title: 'Implement user authentication',
    description: 'Set up OAuth2.0 integration for social logins',
    created_at: new Date('2024-03-02T10:00:00Z'),
    updated_at: new Date('2024-03-05T14:30:00Z'),
    column_id: '3f9502ab-1893-4e8e-a3d2-901d55b234c7',
    column_position: 1,
    attachments: ['https://example.com/auth-spec.pdf'],
  },
  {
    id: 'd2e3f4a5-b6c7-489d-a0e1-f2b3c4d5e6f7',
    title: 'Design homepage layout',
    description: 'Create new responsive homepage design',
    created_at: new Date('2024-03-03T11:15:00Z'),
    updated_at: new Date('2024-03-04T09:45:00Z'),
    column_id: '3f9502ab-1893-4e8e-a3d2-901d55b234c7',
    column_position: 2,
    attachments: [],
  },

  // In Progress Cards
  {
    id: 'e4f5a6b7-c8d9-4e0f-a1b2-c3d4e5f6a7b8',
    title: 'Develop API endpoints',
    description: 'Create RESTful API for user management',
    created_at: new Date('2024-03-10T14:00:00Z'),
    updated_at: new Date('2024-03-12T16:00:00Z'),
    column_id: '6b21c952-3e74-4a1d-b4d1-8a3f5b6c7d8e',
    column_position: 1,
    attachments: ['https://example.com/api-spec.yaml'],
  },
  {
    id: 'f5a6b7c8-d9e0-4f1a-a2b3-c4d5e6f7a8b9',
    title: 'Create database schema',
    description: 'Design PostgreSQL schema for new features',
    created_at: new Date('2024-03-11T09:30:00Z'),
    updated_at: new Date('2024-03-13T11:45:00Z'),
    column_id: '6b21c952-3e74-4a1d-b4d1-8a3f5b6c7d8e',
    column_position: 2,
    attachments: ['https://example.com/erd-diagram.png'],
  },

  // Review Cards
  {
    id: 'a6b7c8d9-e0f1-4a2b-a3c4-d5e6f7a8b9c0',
    title: 'Test login functionality',
    description: 'QA test for social login flows',
    created_at: new Date('2024-03-14T10:00:00Z'),
    updated_at: new Date('2024-03-15T14:15:00Z'),
    column_id: 'c45d9e2f-1a2b-4c3d-9e8f-7a6b5c4d3e2f',
    column_position: 1,
    attachments: ['https://example.com/qa-checklist.pdf'],
  },

  // Testing Cards
  {
    id: 'b7c8d9e0-f1a2-4b3c-a4d5-e6f7a8b9c0d1',
    title: 'User testing phase 1',
    description: 'Conduct UAT with beta users',
    created_at: new Date('2024-03-16T09:00:00Z'),
    updated_at: new Date('2024-03-17T16:30:00Z'),
    column_id: 'd8e7f6a5-4b3c-2d1e-9f0a-8b7c6d5e4f3a',
    column_position: 1,
    attachments: [],
  },

  // Done Cards
  {
    id: 'c8d9e0f1-a2b3-4c4d-a5e6-f7a8b9c0d1e2',
    title: 'Deploy to production',
    description: 'Production deployment v1.2.0',
    created_at: new Date('2024-03-18T08:00:00Z'),
    updated_at: new Date('2024-03-19T12:00:00Z'),
    column_id: 'e9f8a7b6-5c4d-3e2f-1a0b-9c8d7e6f5a4b',
    column_position: 1,
    attachments: ['https://example.com/release-notes.txt'],
  },
];
