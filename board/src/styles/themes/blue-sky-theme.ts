import { definePreset } from '@primeng/themes';
import Aura from '@primeng/themes/aura';

export const BlueSkyTheme = definePreset(Aura, {
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
    // Primary - Productive Blue
    primary: {
      50: 'hsl(210, 100%, 98%)', // Lightest sky
      100: 'hsl(209, 95%, 94%)', // Very light blue
      200: 'hsl(208, 90%, 85%)', // Light blue
      300: 'hsl(207, 85%, 76%)', // Moderate blue
      400: 'hsl(206, 80%, 63%)', // Medium blue
      500: 'hsl(205, 80%, 50%)', // Core blue (primary action color)
      600: 'hsl(209, 85%, 42%)', // Deeper blue
      700: 'hsl(213, 90%, 35%)', // Rich blue
      800: 'hsl(217, 92%, 28%)', // Deep blue
      900: 'hsl(221, 90%, 22%)', // Darkest blue
      950: 'hsl(225, 92%, 15%)', // Near-black blue
    },
    // Status colors for Kanban cards
    green: {
      50: 'hsl(141, 70%, 96%)',
      100: 'hsl(141, 65%, 90%)',
      200: 'hsl(141, 60%, 80%)',
      300: 'hsl(142, 55%, 65%)',
      400: 'hsl(142, 50%, 50%)',
      500: 'hsl(142, 60%, 45%)', // Completed tasks
      600: 'hsl(143, 65%, 38%)',
      700: 'hsl(144, 70%, 32%)',
      800: 'hsl(145, 75%, 26%)',
      900: 'hsl(145, 80%, 20%)',
    },
    amber: {
      50: 'hsl(46, 100%, 96%)',
      100: 'hsl(46, 95%, 90%)',
      200: 'hsl(45, 90%, 80%)',
      300: 'hsl(44, 85%, 70%)',
      400: 'hsl(43, 90%, 60%)',
      500: 'hsl(42, 95%, 55%)', // In progress tasks
      600: 'hsl(38, 100%, 50%)',
      700: 'hsl(35, 95%, 45%)',
      800: 'hsl(32, 90%, 40%)',
      900: 'hsl(28, 85%, 35%)',
    },
    purple: {
      50: 'hsl(260, 70%, 97%)',
      100: 'hsl(259, 65%, 92%)',
      200: 'hsl(258, 60%, 85%)',
      300: 'hsl(257, 55%, 73%)',
      400: 'hsl(256, 50%, 60%)',
      500: 'hsl(255, 60%, 50%)', // Blocked tasks
      600: 'hsl(254, 65%, 45%)',
      700: 'hsl(253, 70%, 40%)',
      800: 'hsl(252, 75%, 30%)',
      900: 'hsl(251, 80%, 25%)',
    },
    red: {
      50: 'hsl(0, 85%, 97%)',
      100: 'hsl(0, 80%, 92%)',
      200: 'hsl(0, 75%, 85%)',
      300: 'hsl(0, 70%, 75%)',
      400: 'hsl(0, 65%, 65%)',
      500: 'hsl(0, 70%, 55%)', // Urgent/priority tasks
      600: 'hsl(0, 75%, 50%)',
      700: 'hsl(0, 80%, 45%)',
      800: 'hsl(0, 85%, 40%)',
      900: 'hsl(0, 90%, 30%)',
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
        shadow: '0 0 0 4px rgba(59, 130, 246, 0.15)',
      },
      transitionDuration: '{transition.duration}',
    },
    colorScheme: {
      light: {
        surface: {
          0: 'hsl(210, 20%, 99%)', // Pure white with slight blue tint
          50: 'hsl(210, 16%, 97%)', // Nearly white
          100: 'hsl(210, 14%, 95%)', // Off-white
          200: 'hsl(210, 12%, 93%)', // Very light gray
          300: 'hsl(210, 10%, 88%)', // Light gray
          400: 'hsl(210, 9%, 81%)', // Medium light gray
          500: 'hsl(210, 8%, 70%)', // Medium gray
          600: 'hsl(210, 7%, 56%)', // Medium dark gray
          700: 'hsl(210, 8%, 43%)', // Dark gray
          800: 'hsl(210, 10%, 30%)', // Very dark gray
          900: 'hsl(210, 12%, 20%)', // Nearly black
          950: 'hsl(210, 15%, 14%)', // Black with blue tint
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
        // Additional theme settings continuing as in original
      },
      dark: {
        surface: {
          950: 'hsl(220, 25%, 9%)', // Deep dark blue-gray
          900: 'hsl(220, 24%, 12%)', // Very dark blue-gray
          800: 'hsl(220, 22%, 16%)', // Dark blue-gray
          700: 'hsl(220, 20%, 22%)', // Medium dark blue-gray
          600: 'hsl(220, 18%, 32%)', // Blue-gray
          500: 'hsl(220, 16%, 42%)', // Medium blue-gray
          400: 'hsl(220, 14%, 55%)', // Light blue-gray
          300: 'hsl(220, 12%, 70%)', // Very light blue-gray
          200: 'hsl(220, 16%, 82%)', // Pale blue-gray
          100: 'hsl(220, 18%, 90%)', // Nearly white blue-gray
          50: 'hsl(220, 20%, 96%)', // Off-white blue-gray
          0: 'hsl(220, 25%, 100%)', // Pure white
        },
        primary: {
          color: '{primary.400}',
          contrastColor: '{surface.900}',
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
        shadow: '0 1px 3px rgba(0,0,0,0.1)',
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
        shadow: '0 1px 2px rgba(0,0,0,0.05)',
        transition: 'all 0.15s ease',
        hoverShadow: '0 3px 6px rgba(0,0,0,0.1)',
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
