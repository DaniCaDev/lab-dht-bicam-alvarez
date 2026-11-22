# API Java de BiCIAM

BiCIAM reúne generadores de soluciones y operadores para problemas de optimización monoobjetivo y multiobjetivo.

- `problem.definition.Problem` configura las funciones objetivo y el operador de búsqueda.
- `problem.definition.State` contiene el código y la evaluación de una solución.
- `metaheurictics.strategy.Strategy` coordina la generación y las iteraciones.
- `metaheuristics.generators.Generator` define el contrato de los generadores de candidatos.
- `local_search.acceptation_type.Dominance` mantiene el frente de Pareto.
- `biciam.demo.BiCIAMDemo` sirve como ejemplo ejecutable con `RandomSearch`.

El código original conserva los nombres de sus paquetes y métodos públicos. La API de `State` copia las listas recibidas y permite que los operadores actualicen las listas del propio estado.
