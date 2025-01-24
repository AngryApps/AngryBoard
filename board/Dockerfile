FROM node:20-slim AS base
ENV PNPM_HOME="/pnpm"
ENV PATH="$PNPM_HOME:$PATH"
RUN corepack enable
RUN corepack prepare pnpm@latest --activate

FROM base AS builder
WORKDIR /app

# Copy package files first for better caching
COPY package*.json pnpm-lock.yaml ./

# Install dependencies using pnpm
RUN --mount=type=cache,id=pnpm,target=/pnpm/store pnpm install --frozen-lockfile

# Install Angular CLI globally
RUN pnpm add -g @angular/cli@latest

# Copy the rest of the application
COPY . .

# Expose port 4200 for the dev server
EXPOSE 4200

# Start the development server with host binding to allow external access
CMD ["ng", "serve", "--host", "0.0.0.0"]
