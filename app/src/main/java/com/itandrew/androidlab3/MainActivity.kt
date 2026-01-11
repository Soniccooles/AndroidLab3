package com.itandrew.androidlab3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

/*
Создать проект в Android Studio с типом Empty views project (Не Empty project с рисунком кубика!). В Activity-layout добавить androidx.fragment.app.FragmentContainerView для
размещения в нём корневого фрагмента с помощью NavigationComponent. Файл MainActivity.kt оставить пустым.
Доступ до view-элементов исключительно с помощью библиотеки ViewBindingPropertyDelegate от kirich1409!
Вся навигация (в нашем случае только главный экран) должна быть реализована с помощью библиотеки NavigationComponent,
граф должен быть представлен в res/navigation/main_graph.xml. Придерживаться паттерна MVVM

Реализовать DI с помощью библиотеки Dagger2. Разделить проект на 4 package: presenter, di, domain, data.

В presenter разместить ui (фрагмент(ы)) и ViewModel.
В di - разместить всё что касается DI (Компонент даггера, модули графа, аннотацию ключа ViewModelKey и ViewModelFactory,
свойство-расширение типа Context для доступа к компоненту.
В data - репозиторий и сервис Retrofit для работы с сетью. Заметьте, методы сервиса должны быть помечены ключевым словом suspend и вызываться из
корутины во viewModel (используйте viewModelScope).
В domain - исключительно UseCase для доступа к модели из package data. Не забывайте про suspend operator fun invoke(), которому можно передавать
параметры в круглые скобки и менять возвращаемый тип.

Итоговое приложение должно уметь включать/выключать лампочку, менять яркость (попробуйте задействовать SeekBar), менять цвет (попробуйте сделать через выпадашку "Spinner").

Обеспечьте себе возможность подменить baseUrl до API, т.к. все endpoint намеренно сделаны абсолютно идентично с боевым сервисом (который работает в аудитории).

Заметьте, что тестовый сервер работает по http (не по https). Вам необходимо разрешить clearText. Смотреть здесь.

Категорически запрещается давать ViewModel доступ как до Context, так и до любого View. Фрагменты должны получать данные исключительно с помощью LiveData
(постарайтесь не торчать наружу MutableLiveData).

Ссылки:
Смотерть за лампочкой: http://195.133.53.179:1337/
Документация к API: http://195.133.53.179:1337/swagger-ui/index.html

Обратите внимание, сервис синхронизирован и работает у всех одновременно. Ваши действия на веб-странице видят все, кто выполняет лабу (считайте что лампочка одна на всех).

Приложите сюда итоговую .apk вашего приложения.

Ниже как комментарий к ответу приложите ссылку на git с Вашим проектом*/
