import { definePreset } from '@primeng/themes';
import Aura from '@primeng/themes/aura';

export const CoffeeTheme = definePreset(Aura, {
  semantic: {
    transitionDuration: '0.2s',
    focusRing: {
      width: '2px',
      style: 'solid',
      color: '{primary.500}',
      offset: '2px',
      shadow: 'none',
    },
    disabledOpacity: '0.6',
    iconSize: '1rem',
    anchorGutter: '2px',
    primary: {
      50: 'hsl(25, 75%, 97%)', // Primary 50 from example
      100: 'hsl(25, 70%, 94%)', // Light cream
      200: 'hsl(25, 65%, 87%)', // Primary 200 from example
      300: 'hsl(25, 60%, 75%)', // Latte
      400: 'hsl(25, 55%, 60%)', // Primary 400 from example
      500: 'hsl(25, 50%, 45%)', // Primary 500 from example (buttons)
      600: 'hsl(25, 55%, 38%)', // Dark roast (button hover)
      700: 'hsl(25, 60%, 32%)', // Primary 700 from example
      800: 'hsl(25, 65%, 25%)', // Dark chocolate
      900: 'hsl(25, 70%, 18%)', // Primary 900 from example
      950: 'hsl(25, 75%, 12%)', // Near-black coffee
    },
    green: {
      50: 'hsl(140, 60%, 97%)',
      100: 'hsl(140, 55%, 92%)',
      200: 'hsl(140, 50%, 84%)',
      300: 'hsl(140, 45%, 70%)',
      400: 'hsl(140, 40%, 56%)',
      500: 'hsl(140, 45%, 46%)', // Green 500 from example (Completed)
      600: 'hsl(140, 50%, 38%)',
      700: 'hsl(140, 55%, 30%)', // Color used in tag-marketing dark mode
      800: 'hsl(140, 60%, 25%)',
      900: 'hsl(140, 65%, 20%)',
    },
    amber: {
      50: 'hsl(35, 100%, 97%)',
      100: 'hsl(35, 95%, 92%)',
      200: 'hsl(35, 90%, 84%)',
      300: 'hsl(35, 85%, 72%)',
      400: 'hsl(35, 80%, 60%)',
      500: 'hsl(35, 85%, 53%)', // Amber 500 from example (In Progress)
      600: 'hsl(30, 90%, 48%)',
      700: 'hsl(25, 95%, 43%)',
      800: 'hsl(20, 90%, 38%)',
      900: 'hsl(15, 85%, 33%)',
    },
    purple: {
      50: 'hsl(280, 65%, 97%)', // Used in tag-dev background
      100: 'hsl(280, 60%, 92%)',
      200: 'hsl(280, 55%, 85%)',
      300: 'hsl(280, 50%, 73%)',
      400: 'hsl(280, 45%, 60%)',
      500: 'hsl(280, 50%, 48%)', // Purple 500 from example (Blocked)
      600: 'hsl(280, 55%, 42%)',
      700: 'hsl(280, 60%, 36%)', // Color used in tag-dev text
      800: 'hsl(280, 65%, 30%)',
      900: 'hsl(280, 70%, 24%)',
    },
    red: {
      50: 'hsl(10, 85%, 97%)',
      100: 'hsl(10, 80%, 92%)',
      200: 'hsl(10, 75%, 86%)',
      300: 'hsl(10, 70%, 75%)',
      400: 'hsl(10, 65%, 65%)',
      500: 'hsl(10, 70%, 55%)', // Red 500 from example (Urgent)
      600: 'hsl(10, 75%, 48%)',
      700: 'hsl(10, 80%, 42%)',
      800: 'hsl(10, 85%, 36%)',
      900: 'hsl(10, 90%, 30%)',
    },
    formField: {
      paddingX: '0.75rem',
      paddingY: '0.5rem',
      sm: {
        fontSize: '0.875rem',
        paddingX: '0.625rem',
        paddingY: '0.375rem',
      },
      lg: {
        fontSize: '1.125rem',
        paddingX: '0.875rem',
        paddingY: '0.625rem',
      },
      borderRadius: '{border.radius.md}',
      focusRing: {
        width: '2px',
        style: 'solid',
        color: '{primary.400}',
        offset: '0',
        shadow: '0 0 0 4px rgba(179, 136, 101, 0.2)',
      },
      transitionDuration: '{transition.duration}',
    },
    colorScheme: {
      light: {
        surface: {
          0: 'hsl(30, 25%, 99%)', // Surface 0 from example (background)
          50: 'hsl(30, 20%, 97%)', // Used for column background
          100: 'hsl(30, 18%, 95%)', // Surface 100 from example
          200: 'hsl(30, 16%, 92%)', // Used for borders
          300: 'hsl(30, 14%, 87%)', // Surface 300 from example
          400: 'hsl(30, 12%, 80%)', // Cappuccino
          500: 'hsl(30, 10%, 68%)', // Surface 500 from example
          600: 'hsl(30, 8%, 54%)', // Coffee
          700: 'hsl(30, 10%, 42%)', // Surface 700 from example (card-desc)
          800: 'hsl(30, 12%, 30%)', // Used for main text color
          900: 'hsl(30, 14%, 20%)', // Surface 900 from example
          950: 'hsl(30, 16%, 12%)', // Near-black coffee
        },
        primary: {
          color: '{primary.500}',
          contrastColor: '#ffffff',
          hoverColor: '{primary.600}',
          activeColor: '{primary.700}',
        },
        highlight: {
          background: '{primary.50}',
          focusBackground: '{primary.100}',
          color: '{primary.700}',
          focusColor: '{primary.800}',
        },
        mask: {
          background: 'rgba(0,0,0,0.4)',
          color: '{surface.200}',
        },
      },
      dark: {
        surface: {
          950: 'hsl(20, 30%, 8%)', // Dark mode background
          900: 'hsl(20, 28%, 12%)', // Dark mode column
          800: 'hsl(20, 26%, 16%)', // Dark mode borders
          700: 'hsl(20, 24%, 22%)', // Medium dark brown
          600: 'hsl(20, 22%, 32%)', // Brown
          500: 'hsl(20, 20%, 42%)', // Medium brown
          400: 'hsl(20, 18%, 55%)', // Light brown
          300: 'hsl(20, 16%, 70%)', // Dark mode card-desc
          200: 'hsl(20, 20%, 82%)', // Pale brown
          100: 'hsl(20, 22%, 90%)', // Nearly white brown
          50: 'hsl(20, 24%, 96%)', // Off-white brown
          0: 'hsl(20, 30%, 100%)', // Pure white
        },
        primary: {
          color: '{primary.400}',
          contrastColor: '{surface.950}',
          hoverColor: '{primary.300}',
          activeColor: '{primary.200}',
        },
        highlight: {
          background: 'color-mix(in srgb, {primary.400}, transparent 84%)',
          focusBackground: 'color-mix(in srgb, {primary.400}, transparent 76%)',
          color: 'hsla(0, 0%, 100%, 0.87)',
          focusColor: 'hsla(0, 0%, 100%, 0.87)',
        },
        // Additional dark theme settings continuing as in original
      },
    },
  },
  // Custom components specific to Kanban functionality
  components: {
    kanbanColumn: {
      root: {
        background: '{surface.50}',
        borderRadius: '{border.radius.lg}',
        shadow: '0 2px 4px rgba(90, 60, 30, 0.08)',
        transition: 'all 0.2s ease',
      },
      header: {
        padding: '0.75rem',
        background: 'transparent',
        borderBottom: '1px solid {surface.200}',
      },
      body: {
        padding: '0.5rem',
      },
      footer: {
        padding: '0.75rem',
        borderTop: '1px solid {surface.200}',
      },
    },
    kanbanCard: {
      root: {
        background: '{surface.0}',
        borderRadius: '{border.radius.md}',
        padding: '0.75rem',
        margin: '0.5rem 0',
        shadow: '0 1px 3px rgba(90, 60, 30, 0.1)',
        transition: 'all 0.15s ease',
        hoverShadow: '0 3px 6px rgba(90, 60, 30, 0.15)',
        borderLeft: '3px solid {primary.500}',
      },
      title: {
        fontSize: '0.9rem',
        fontWeight: '600',
        marginBottom: '0.5rem',
      },
      description: {
        fontSize: '0.8rem',
        color: '{surface.700}',
      },
      priority: {
        high: {
          borderLeftColor: '{red.500}',
        },
        medium: {
          borderLeftColor: '{amber.500}',
        },
        low: {
          borderLeftColor: '{green.500}',
        },
        blocked: {
          borderLeftColor: '{purple.500}',
        },
      },
    },
  },
});
