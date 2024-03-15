
![image](https://github.com/alexiashirazi/Baza-de-date/assets/132008022/f3e56a30-e555-4882-8c69-1dc3fb932aec)

1.	Tema Proiectului
	Proiectul propus vizează dezvoltarea unei aplicații cu interfață grafică utilizând JavaFX pentru gestionarea eficientă a unei platforme educaționale. Această platformă utilizează un sistem de gestionare a bazelor de date MySQL pentru a oferi funcționalități specifice diferitelor roluri de utilizatori, inclusiv administratori, profesori și studenți.

Accesul la aplicație este securizat prin autentificare, unde utilizatorii, împărțiți în categorii precum super administratori, administratori, profesori și studenți, folosesc adresa de email și CNP-ul. Erori în introducerea acestor date generează mesaje de eroare pentru a asigura autenticitatea accesului.

Interfața grafică, dezvoltată cu JavaFX, oferă funcționalități personalizate pentru fiecare categorie de utilizatori:

	Student:

•	Vizualizare activități în calendar și setare evenimente personale.
•	Acces la listă completă a studenților și profesorilor.
•	Vizualizare și descărcare a propriilor note.
•	Pagini dedicate informațiilor personale, comunicare prin chat cu colegii și acces la grupurile de studiu.
	Profesor:

•	Vizualizare activități în calendar și setare evenimente personale.
•	Acces la listă completă a studenților și colegilor.
•	Gestionare a catalogului propriu, cu posibilitatea modificării procentajelor pe tipuri de note.
•	Pagini dedicate informațiilor personale și înscriere în grupurile de studiu.
	Administrator:

•	Acces la pagini dedicate gestionării informațiilor despre studenți, profesori și administratori.
•	Super administratorii au acces la o pagină suplimentară cu funcționalități avansate, cum ar fi editarea datelor despre administratori și administrarea acestora.
Implementarea interfeței grafice cu JavaFX contribuie la crearea unei experiențe utilizator coerente și interactive, asigurând în același timp portabilitate și performanță optimă.
	


2.	Proiectarea conceptuală a bazei de date

 ![image](https://github.com/alexiashirazi/Baza-de-date/assets/132008022/370ec92d-d16f-490c-a057-97df375d04de)





3.	Soluția de transformare în relațional
		
	           În cadrul modelului relational al bazei de date, fiecare tabel este definit cu cheia sa primară distinctă și relațiile cu alte tabele sunt stabilite prin intermediul cheilor străine. 
	Tabelul "activitate_didactica" utilizează cheile străine idProfesor și idMaterie, care referențiază, respectiv, tabelele "profesor" și "materie". 
	Tabela "activitate_grup" este legată de tabela "grup_studenti" prin cheia străină id_grup.
	Tabela "catalog" utilizează cheile străine id_student și id_materie pentru referințele către tabelele "student" și "materie". 
	Tabela "chat" stabilește legăturile cu tabelele "student" și "grup_studenti" prin cheile străine idStudent și, respectiv, idGrup. 
	Alte tabele precum "inscris_activitate_didactica", "inscris_activitate_grup", "inscris_grup", și "inscris_profesor_materie" utilizează chei străine pentru a conecta tabelele "activitate_didactica", "activitate_grup", "grup_studenti", "profesor", "materie", și "student". 
	Aceste relații sunt de tipul "Many-to-Many", unde entitățile dintr-un tabel pot fi asociate cu mai multe entități din alt tabel și vice-versa.

4.	Descrierea bazei de date relaționale
Tabele:
	activitate_didactica- 
Folosită pentru a stoca informații despre cursuri, seminarii, laboratoare, examene etc. Legată de tabelele "profesor" și "materie" prin cheile străine.
	activitate_grup-
Folosită pentru a stoca informațiile legate de grupurile de studii,legături către tabela "grup_studenti" prin cheia străină.
	administrator- 
Stocarea informațiilor despre administratorii platformei, inclusiv detalii personale și atribuția statutului de superadministrator pentru  anumite privilegii.
	catalog
Menținerea datelor referitoare la notele obținute de studenți în diverse activități didactice, inclusiv cursuri, seminarii și laboratoare, cu referințe către tabelele "materie" și "student".
	chat
Stocarea conversațiilor în cadrul grupurilor de studenți, legată de tabelele "student" și "grup_studenti" prin cheile străine.
	grup_studenți
Folosită pentru a organiza studenții în grupuri asociate cu anumite activități sau proiecte, cu referințe la tabela "materie".
	înscris_activitate_didactică
Menținerea legăturii dintre studenți și activitățile didactice la care sunt înscriși, cu referințe către tabelele "activitate_didactica" și "student".


	înscris_activitate_grup
Stocarea relațiilor dintre studenți și activitățile de grup la care participă, cu referințe la tabelele "activitate_grup" și "student".
	înscris_grup
Menținerea asocierilor dintre studenți și grupurile de studenți, cu referințe la tabela "grup_studenti".
	înscris_profesor_materie
Stocarea legăturilor dintre profesori și materiile pe care le predau, cu referințe la tabelele "profesor" și "materie".
	materie
Utilizată pentru a păstra informații despre disciplinele de studiu, inclusiv detalii despre orele de curs, seminar, laborator, și altele.
	profesor
Stocarea informațiilor despre profesori, inclusiv detalii personale și atribuții referitoare la materiile predate.
	profesor_activitate_grup
Stocarea relațiilor dintre profesori și activitățile de grup la care sunt asignați, cu referințe la tabelele "profesor" și "activitate_grup". A fost creată special pentru posibilitatea de a adăuga un cadru didactic grupului de studiu.
	Student
Păstrarea detaliilor personale ale studenților, inclusiv date de contact și informații academice precum anul de studiu și numărul de ore alocate studiului.


5.	Argumentarea nivelului de normalizare
	Toate tabelele din baza de date respectă criteriile pentru normalizare, garantând astfel o structură eficientă și coerentă a datelor. Tabele precum activitate_didactica și activitate_grup sunt proiectate conform formei normale a treia, eliminând dependențele tranzitive și menținând coerența datelor. Tabelele cum ar fi administrator și student sunt în conformitate cu forma normală de bază, asigurând organizarea datelor în structuri simple și atomice. În plus, relațiile dintre entități sunt gestionate corespunzător prin tabele precum înscris_activitate_didactica și înscris_profesor_materie, care respectă formele normale relevante. Prin aplicarea normalizării, baza de date devine mai ușor de întreținut, cu redundanță minimă și asigurând integritatea datelor.



6.	Codul in MYSQL
Am folosit 8 proceduri, acestea fiind:

1)	ActivitățiProfesor-am folosit această procedură pentru a putea pune în tabelă activitățile la care profesorul s-a înscris
2)	AdaugăProfesorLaActivitate-am folosit această procedură pentru a înrola profesorul la activitate
3)	GetStudentActivities-am folosit această procedură pentru tabela de sugestii a studentului(oameni cunoscuți înscriși la aceleași activități)
4)	GetStudentGrades-am folosit această procedură pentru catalogul studentului
5)	InroleazaStudent-am folosit această procedură pentru înrolarea studentului la grupul de studiu
6)	StergeProfesorDinActivitate-am folosit această procedură pentru stergerea profesorului din grupul de studiu
7)	StergeStudentDinCurs-am folosit această procedură pentru stergerea studentului din grupul de studiu
8)	UpdateFinalGrades-am folosit această procedură pentru actualizarea notelor dupa restabilirea procentajelor la materii
9)	GetActivitiesForGroup-am folosit această procedură pentru a afișa sugestii de studenți pentru grupul de studiu


7.	Funcționalitatea aplicației
	
	În realizarea aplicației, am structural codul pe 3 pachete. În primul pachet am pus pozele pe care le-am utilizat, în cel de al doilea am creat clasele Java, iar în al treilea am implementat clasele de tipul FXML pentru realizarea interfeței.










Ferestre din cadrul aplicației:
Fereastra de logare
![image](https://github.com/alexiashirazi/Baza-de-date/assets/132008022/c881d157-a221-4de1-bb8b-3a886e2f9f9b)


 	

Fereastra de student
 
![image](https://github.com/alexiashirazi/Baza-de-date/assets/132008022/c253796e-b8bd-4fe7-a2dd-5675ec36cd6f)





Fereastra About You Student
![image](https://github.com/alexiashirazi/Baza-de-date/assets/132008022/1973adf5-0817-4d00-a272-7ec74872d156)

 


Catalog Profesori
 ![image](https://github.com/alexiashirazi/Baza-de-date/assets/132008022/db148032-f278-4658-94be-f835149cc207)




Stabilire Procentaj
![image](https://github.com/alexiashirazi/Baza-de-date/assets/132008022/208f00a6-5f75-4cbd-99a3-e210355cec05)

 


Posibilitatea profesorului de a se înrola un grup de studiu
 ![image](https://github.com/alexiashirazi/Baza-de-date/assets/132008022/413d1e84-743c-437b-953a-8ea6dc5d8aa4)



			

7.  Manual de utilizare

	O dată pornită aplicația se va deschide fereastra de autentificare. Ne vom autentifica cu email și CNP ul. Dacă datele introduse sunt corecte vom putea accesa pagina de care aparținem(student, profesor, respectiv administrator). 

	Pagina studentului
După autentificare, un student va avea acces la pagina principală care conține informații generale despre activitățile sale, cum ar fi evenimentele viitoare din calendar și notificări relevante.
Studentul poate vizualiza și descărca propriile note accesând secțiunea "Catalog", unde sunt afișate notele obținute la diverse activități didactice.
În secțiunea "Study Group", studentul poate să se înscrie în diverse grupuri asociate cu materiile de studiu sau proiecte.
Secțiunea "Chat" oferă posibilitatea de a comunica cu colegii din grupurile de studiu.
Prin intermediul secțiunii "About You", studentul poate actualiza informațiile personale și poate vizualiza detaliile propriului profil.

	Pagina Profesorului:

Profesorul va avea acces la un panou de control care include informații despre activitățile didactice, grupurile de studiu și notificări relevante.
În secțiunea "Catalog Profesori", profesorul poate gestiona notele acordate studenților la diverse activități didactice.
Prin secțiunea "Stabilire Procentaj", profesorul poate modifica procentajul alocat fiecărui tip de activitate în catalog.
Profesorul are posibilitatea să se înroleze în diverse grupuri de studiu pentru a participa activ la proiecte sau activități extra-curriculare.
Actualizarea informațiilor personale și vizualizarea profilului se realizează prin secțiunea "About You".





	Pagina Administratorului:

Administratorul are acces la informații detaliate despre studenți, profesori și alți administratori.
Prin intermediul secțiunii "Gestionare Administratori", administratorul poate adăuga, actualiza sau șterge conturi de administratori, iar super administratorii au acces la funcționalități avansate.
Secțiunea "Teachers" oferă posibilitatea de a adăuga, actualiza sau șterge conturi de profesori.
Prin secțiunea "Students", administratorul poate gestiona informațiile despre studenți, inclusiv înrolarea și dezînrolarea acestora din grupuri de studiu.
Actualizarea informațiilor personale și vizualizarea profilului se realizează prin secțiunea "About You".
	Pagina superadministratorului
    Superadministratorul are toate rolurile admnistratorului doar ca poate edita, sterge, adauga informații despre administratori.



8.  Concluzii

	Proiectul propus, un sistem de gestionare a unei platforme educaționale, abordează eficient nevoile diferitelor categorii de utilizatori: studenți, profesori și administratori.
Interfața grafică dezvoltată cu JavaFX oferă o experiență utilizator coerentă și interactivă, facilitând accesul și utilizarea platformei.
	Modelul relational al bazei de date asigură o structură eficientă și coerentă a datelor, cu relații bine definite între entități.
Normalizarea bazei de date contribuie la eliminarea redundanțelor și menținerea integrității datelor.
	Codul în MySQL este optimizat prin utilizarea procedurilor stocate pentru diverse operații, oferind astfel o performanță îmbunătățită.
Proiectul reușește să integreze aspecte importante precum securitatea autentificării, funcționalități personalizate pentru fiecare categorie de utilizatori și o bază de date bine proiectată, contribuind la eficiența și ușurința de utilizare a platformei educaționale.






9.  Posibile dezvoltări viitoare

	Adăugarea funcționalităților de raportare și analiză a datelor pentru administratori.
Implementarea unui sistem de notificare în timp real pentru activitățile relevante.
	Dezvoltarea unei interfețe mobile pentru a facilita accesul la platformă de pe dispozitive mobile.
	Extinderea funcționalităților de chat și colaborare între utilizatori.
	Implementarea unui sistem de evaluare a cursurilor și activităților didactice.
	Proiectul oferă o fundație solidă pentru gestionarea eficientă a unei platforme educaționale și poate fi adaptat și extins pentru a satisface cerințele specifice ale unei instituții de învățământ.
