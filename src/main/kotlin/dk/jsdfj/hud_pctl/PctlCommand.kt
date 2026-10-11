package dk.jsdfj.hud_pctl

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PctlCommand(val name: String)
