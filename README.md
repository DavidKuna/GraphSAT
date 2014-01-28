GraphSAT
========

Graph problems solver

Autor: David Kuna (KUN0036)

Řešené problémy:
	1. Graph coloring
		- Jsou vytvořeny proměnné pro všechny kombinace vrcholů a barev
		- Formule se skládá z omezení pro vrcholy (musí platit právě jedna barva pro každý vrchol) a z omezení pro hrany (vrcholy spojené hranou nesmí být stejné barvy)
		- Výsledné ohodnocení je vypsáno ve větě sdělující číslo barvy náležící danému vrcholu.
		- Spuštění: java -jar GraphSAT.jar cesta/ke/zdroji.txt 1 k

	2. Independet set
		- Jsou vytvořeny proměnné pro jednotlivé vrcholy (Vi) a pro kombinaci všech vrcholů a velikosti množiny k (Xi,j). Tato pomocná matice umožní sestavit formuli omezení přiřazující jednotlivým vrcholům přesnou pozici v hledané množině.
		- Formule se skádá z omezení pro hrany, sloupce matice (maximálně jedna 1 ve sloupci), řádky matice (právě jedna 1 na řádku)
		- Výsledek je zapsán jako množina indexů nezávislých vrcholů.
		- Spuštění: java -jar GraphSAT.jar cesta/ke/zdroji.txt 2 k

	3. Hamilton circle
		- Jsou vytvořeny proměnné pro všechny kombinace vrcholů a jejich pozic. Tedy n^2 proměnných.
		- Formule obsahuje omezení pro samostatné vrcholy (každý vrchol zastupuje právě jednu pozici v HC), omezení pro všechny kombinace proměnných zajišťující, že každá pozice bude obsazena právě jednou a omezení pro všechny dvojice vrcholů, které nejsou spojeny hranou (nemůžou být na pozicích vedle sebe)
		- Výsledný kruh je znázorněn směrem průchodu přes jednotlivé vrcholy.
		- Spuštění: java -jar GraphSAT.jar cesta/ke/zdroji.txt 3

Externí zdroje:
	- Program využívá externí knihovnu Sat4j umístěnou v lib/org.sat4j.core-2.2.3.jar
	- http://www.sat4j.org/doc.php

Kompilace:
	Balík obsahuje projekt vývojového prostředí NetBeans v7.4

