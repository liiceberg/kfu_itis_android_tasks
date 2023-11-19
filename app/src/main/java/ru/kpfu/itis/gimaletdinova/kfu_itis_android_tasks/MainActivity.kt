package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controller =
            (supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment)
                .navController

        findViewById<BottomNavigationView>(R.id.menu).apply {
            setupWithNavController(controller)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(POST_NOTIFICATIONS),
            POST_NOTIFICATIONS_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            POST_NOTIFICATIONS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_DENIED) {
                    if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                        AlertDialog.Builder(this)
                            .setTitle(getString(R.string.permission_denied_pattern))
                            .setMessage(getString(R.string.permission_denied_first_desc))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                                dialog.cancel()
                                requestPermission()
                            }
                            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                                dialog.cancel()
                                showPermissionDeniedDialog()
                            }
                            .show()

                    } else {
                        showPermissionDeniedDialog()
                    }
                }
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_denied_pattern))
            .setMessage(getString(R.string.permission_denied_desc))
            .setCancelable(false)
            .show()
    }

    companion object {
        private const val POST_NOTIFICATIONS_PERMISSION_CODE = 101
    }

}

/*
Домашнее задание №4 - Coroutines, Permissions, Notifications
Дедлайн: 23.11 - 100%, 30.11 - 50%

Обязательная часть:

Описание UI-элементов:
- Главный экран: 2 поля для ввода текста, кнопка.
- Экран настройки уведомлений: чекбоксы с настройками уведомлений
- Экран с настройками корутин - подробнее ниже
** Каждый экран - это отдельная вкладка в BottomNavigationView

Описание логики работы приложения
- На старте, если приложение запускается на 13-ом андроиде и выше, проверять, дал ли пользователь разрешение на получение уведомлений. Если не дал - запрашивать. Если отклонил более 2 раз - показывать соответствующее сообщение с просьбой включить получение уведомлений в настройках

Главный экран:
- В первое текстовое поле вводится заголовок уведомления, во второе поле - текст сообщения, который будет отображаться в теле уведомления
- По нажатию на кнопку "Показать уведомление" - показать уведомление с набранным текстом и выбранными настройками

Экран настройки уведомления:
- Блок настройки важности уведомления - это может быть выпадающий список, чекбоксы, радиогруппа. Смысл в том, что за раз можно выбрать только одну опцию (достаточно будет 3 вариантов - Medium, High, Urgent)

- Блок настройки приватности уведомления - реализация такая же, как в блоке выше.

- Чекбокс для настройки подробного текста сообщения - если флажок установлен и ваше сообщение достаточно длинное, дать возможность раскрывать содержимое уведомления

- Чекбокс для настройки отображения кнопок - если флажок установлен, в уведомлении должно отображаться 2 кнопки. По нажатию на первую кнопку запускать приложение и показывать Toast/Snackbar с любым текстом. По нажатию на вторую кнопку - запускать приложение и открывать боттом шит/экран с настройками уведомлений

- По нажатию на само уведомление - просто открывать приложение

Экран с настройками корутин:
- Все корутины должны работать в IO потоке и возвращаться в главный поток после выполнения
- Seekbar изменяется от 1 до 10, с шагом 1, отвечает за количество запускаемых корутин
- Чекбокс "async" - если включен, корутины должны выполняться параллельно, если выключен - последовательно
- Чекбокс "Stop on background" - если включен, при сворачивании приложения завершать все активные корутины. Выводить в логи, сколько корутин было завершено. Если выключен - оставить стандартное поведение
- Кнопка "Выполнить" - запустить корутины с выбранными настройками. Если пользователь покидает экран и переключается на другой экран, корутины должны продолжать свое выполнение (дефолтное поведение)

- Когда все корутины завершили свою работу - показывать уведомление в духе "my job here is done".
-
**Что здесь может потребоваться - у корутин билдеров есть флаг отложенного запуска start = CoroutineStart.LAZY.

Опциональная часть
1) Реализовать поддержку ссылок следующего вида:
https://romanov/itis/11-117. Т.е. https://{ваша_фамилия_латиницей}/itis/{номер_группы}/
Для того, чтобы реализовать более сложную логику перехода внутри приложения, нужен Google Play, поэтому по нажатию на ссылку - просто открывать приложение.
Как это будет выглядеть - пользователь нажимает на ссылку, показывается системный chooser с приложением, которое обработает эту ссылку, ваше приложение должно быть в списке. Ваше приложение должно обрабатывать ТОЛЬКО ссылки с указанным шаблоном

2) Реализовать реакцию приложения на  режим"Полёт". Если пользователь активировал режим "Полёт" - делать кнопки "Отправить уведомление" и "Выполнить" неактивными. Показывать поверх контейнера фрагментов вьюшку с предупреждением "Нет доступа к сети". Если пользователь выключил режим "Полёт" - скрывать вьюшку с предупреждением и возвращать стандартное поведение.
Нужно поддерживать как старт приложения с заранее включенным режимом "Полёт", так и динамическое включение/выключение режима во время работы приложения

3) Дополнить логику запроса разрешения на показ уведомлений. Если пользователь отклонил разрешение более двух раз - показывать диалог с информацией и кнопкой. По нажатию на кнопку - переходить в настройки приложения
Чтобы эмулировать "долгие задачи", внутри функции, которая будет запускаться в корутине, можно выставить delay(timeInMillis)

Ограничения: использовать стандартные средства андроида без сторонних библиотек. Что потребуется из стороннего - зависимости Корутин, Lifecycle.
Glide/Coil/Jetpack Navigation/Cicerone - по желанию

Что должно быть на скринкасте:
- На главном экране ввести заголовок и текст уведомления, нажать на "Показать уведомление". Показать, что уведомление отображается в верхней шторке

- Открыть экран с настройками уведомлений, проставить настройки, вернуться на главный экран, нажать на кнопку "Показать уведомление", открыть верхнюю шторку

- Открыть экран с настройками корутин, выставить любые настройки в каждом пункте. Нажать на "Выполнить", дождаться показа уведомления о завершении работы корутин

- Если выполняется опциональная часть - для первого и третьего задания скринкаст не нужен. Для второго - открыть верхнюю шторку, включить авиарежим, показать главный экран и экран запуска корутин, выключить авиарежим, снова показать главный экран.
 */