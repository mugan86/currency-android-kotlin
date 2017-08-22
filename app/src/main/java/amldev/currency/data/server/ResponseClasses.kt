package data

/***********************************************************************************************************************
 * Created by Anartz Mugika on 22/07/2017.
 * ---------------------------------------------------------------------------------------------------------------------
 * Estas clases servirán para obtener la información del servidor y añadirla directamente mediante la librería de GSON.
 * Después dentro de CurrencyDataMapper se gestionará para modelarlo a nuestro gusto con las funciones correspondientes
 * y la definición que hemos realizado dentro del paquete "model" donde encontramos el fichero "DomainClasses" que es
 * donde se define la forma que tendrá la información al final del proceso de la petición
 ***********************************************************************************************************************/
data class CurrencyResult(val base: String, val date: String, val rates: Rates)

data class Rates(val AUD: Double = 1.0,
                 val CAD: Double = 1.0,
                 val CHF: Double = 1.0,
                 val CNY: Double = 1.0,
                 val EUR: Double = 1.0,
                 val GBP: Double = 1.0,
                 val INR: Double = 1.0,
                 val JPY: Double = 1.0,
                 val MYR: Double = 1.0,
                 val RUB: Double = 1.0,
                 val SGD: Double = 1.0,
                 val USD: Double = 1.0)