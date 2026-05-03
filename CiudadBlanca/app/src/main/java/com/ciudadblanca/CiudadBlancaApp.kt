package com.ciudadblanca

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.ciudadblanca.ui.components.CiudadBlancaBottomBar
import com.ciudadblanca.ui.screens.guide.*
import com.ciudadblanca.ui.screens.profile.*
import com.ciudadblanca.ui.screens.reservation.*
import com.ciudadblanca.ui.screens.rooms.*
import com.ciudadblanca.ui.screens.services.*
import com.ciudadblanca.ui.theme.CiudadBlancaTheme
import com.ciudadblanca.ui.screens.auth.AuthScreen
import com.ciudadblanca.ui.screens.auth.SplashScreen
import com.ciudadblanca.ui.screens.guide.AttractionDetailScreen
import com.ciudadblanca.ui.screens.guide.GuideScreen
import com.ciudadblanca.ui.screens.guide.sampleAttractions
import com.ciudadblanca.ui.screens.home.HomeScreen
import com.ciudadblanca.ui.screens.profile.EditProfileScreen
import com.ciudadblanca.ui.screens.profile.GuestProfileScreen
import com.ciudadblanca.ui.screens.profile.MyRequestsScreen
import com.ciudadblanca.ui.screens.profile.MyReservationsScreen
import com.ciudadblanca.ui.screens.profile.ProfileScreen
import com.ciudadblanca.ui.screens.reservation.ReservationDatesScreen
import com.ciudadblanca.ui.screens.reservation.ReservationGuestsScreen
import com.ciudadblanca.ui.screens.reservation.ReservationRoomScreen
import com.ciudadblanca.ui.screens.reservation.ReservationState
import com.ciudadblanca.ui.screens.reservation.ReservationSummaryScreen
import com.ciudadblanca.ui.screens.rooms.RoomDetailScreen
import com.ciudadblanca.ui.screens.rooms.RoomsScreen
import com.ciudadblanca.ui.screens.rooms.sampleRooms
import com.ciudadblanca.ui.screens.services.ServiceRequestScreen
import com.ciudadblanca.ui.screens.services.ServiceSuccessScreen
import com.ciudadblanca.ui.screens.services.ServicesScreen
import com.ciudadblanca.ui.screens.services.hotelServices

// ── Routes ────────────────────────────────────────────────────────────────────
object Routes {
    const val SPLASH       = "splash"
    const val AUTH         = "auth"
    const val HOME         = "home"
    const val ROOMS        = "rooms"
    const val ROOM_DETAIL  = "room_detail/{roomId}"
    const val RESERVE_DATES   = "reserve_dates"
    const val RESERVE_GUESTS  = "reserve_guests"
    const val RESERVE_ROOM    = "reserve_room"
    const val RESERVE_SUMMARY = "reserve_summary"
    const val SERVICES        = "services"
    const val SERVICE_REQUEST = "service_request/{serviceId}"
    const val SERVICE_SUCCESS = "service_success"
    const val GUIDE           = "guide"
    const val ATTRACTION      = "attraction/{attractionId}"
    const val PROFILE         = "profile"
    const val EDIT_PROFILE    = "edit_profile"
    const val MY_RESERVATIONS = "my_reservations"
    const val MY_REQUESTS     = "my_requests"
}

// Screens that show the bottom nav bar
private val bottomNavRoutes = setOf(
    Routes.HOME, Routes.ROOMS, Routes.SERVICES, Routes.GUIDE, Routes.PROFILE
)

// ── Main App ──────────────────────────────────────────────────────────────────
@Composable
fun CiudadBlancaApp() {
    CiudadBlancaTheme {
        val navController = rememberNavController()
        val navBackStack  by navController.currentBackStackEntryAsState()
        val currentRoute  = navBackStack?.destination?.route ?: ""

        // App-level state
        var isLoggedIn by remember { mutableStateOf(false) }
        var isGuest    by remember { mutableStateOf(false) }
        var userName   by remember { mutableStateOf("Invitado") }
        var userEmail  by remember { mutableStateOf("") }

        // Reservation wizard state
        var reservationState by remember { mutableStateOf(ReservationState()) }

        val showBottomBar = currentRoute in bottomNavRoutes

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    CiudadBlancaBottomBar(
                        currentRoute = currentRoute,
                        onNavigate   = { route ->
                            navController.navigate(route) {
                                popUpTo(Routes.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState    = true
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController    = navController,
                startDestination = Routes.SPLASH,
                modifier         = Modifier.padding(innerPadding)
            ) {

                // ── Splash ────────────────────────────────────────────────────
                composable(Routes.SPLASH) {
                    SplashScreen(
                        onFinish = {
                            navController.navigate(Routes.AUTH) {
                                popUpTo(Routes.SPLASH) { inclusive = true }
                            }
                        }
                    )
                }

                // ── Auth ──────────────────────────────────────────────────────
                composable(Routes.AUTH) {
                    AuthScreen(
                        onLoginSuccess = {
                            isLoggedIn = true
                            isGuest = false
                            userName = "dalic"
                            userEmail = "dalic@gmail.com"
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.AUTH) { inclusive = true }
                            }
                        },
                        onGuestContinue = {
                            isGuest = true
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.AUTH) { inclusive = true }
                            }
                        }
                    )
                }

                // ── Home ──────────────────────────────────────────────────────
                composable(Routes.HOME) {
                    HomeScreen(
                        userName = if (isLoggedIn) userName else "Invitado",
                        isGuest = !isLoggedIn,
                        onNavigateRooms = { navController.navigate(Routes.ROOMS) },
                        onNavigateReservation = { navController.navigate(Routes.RESERVE_DATES) },
                        onNavigateServices = { navController.navigate(Routes.SERVICES) },
                        onNavigateGuide = { navController.navigate(Routes.GUIDE) },
                        onRegisterPrompt = { navController.navigate(Routes.AUTH) }
                    )
                }

                // ── Rooms ─────────────────────────────────────────────────────
                composable(Routes.ROOMS) {
                    RoomsScreen(
                        onBack = { navController.popBackStack() },
                        onRoomClick = { room -> navController.navigate("room_detail/${room.id}") }
                    )
                }

                composable(
                    route     = Routes.ROOM_DETAIL,
                    arguments = listOf(navArgument("roomId") { type = NavType.IntType })
                ) { backStack ->
                    val roomId = backStack.arguments?.getInt("roomId") ?: return@composable
                    val room   = sampleRooms.first { it.id == roomId }
                    RoomDetailScreen(
                        room = room,
                        onBack = { navController.popBackStack() },
                        onReserve = {
                            reservationState = ReservationState(selectedRoom = room)
                            navController.navigate(Routes.RESERVE_DATES)
                        }
                    )
                }

                // ── Reservation wizard ────────────────────────────────────────
                composable(Routes.RESERVE_DATES) {
                    ReservationDatesScreen(
                        state = reservationState,
                        onStateChange = { reservationState = it },
                        onBack = { navController.popBackStack() },
                        onContinue = { navController.navigate(Routes.RESERVE_GUESTS) }
                    )
                }

                composable(Routes.RESERVE_GUESTS) {
                    ReservationGuestsScreen(
                        state = reservationState,
                        onStateChange = { reservationState = it },
                        onBack = { navController.popBackStack() },
                        onContinue = { navController.navigate(Routes.RESERVE_ROOM) }
                    )
                }

                composable(Routes.RESERVE_ROOM) {
                    ReservationRoomScreen(
                        state = reservationState,
                        onStateChange = { reservationState = it },
                        onBack = { navController.popBackStack() },
                        onContinue = { navController.navigate(Routes.RESERVE_SUMMARY) }
                    )
                }

                composable(Routes.RESERVE_SUMMARY) {
                    ReservationSummaryScreen(
                        state = reservationState,
                        onBack = { navController.popBackStack() },
                        onConfirm = {
                            navController.navigate(Routes.MY_RESERVATIONS) {
                                popUpTo(Routes.HOME)
                            }
                        }
                    )
                }

                // ── Services ──────────────────────────────────────────────────
                composable(Routes.SERVICES) {
                    ServicesScreen(
                        currentRoute = currentRoute,
                        onServiceClick = { svc -> navController.navigate("service_request/${svc.id}") }
                    )
                }

                composable(
                    route     = Routes.SERVICE_REQUEST,
                    arguments = listOf(navArgument("serviceId") { type = NavType.IntType })
                ) { backStack ->
                    val svcId   = backStack.arguments?.getInt("serviceId") ?: return@composable
                    val service = hotelServices.first { it.id == svcId }
                    ServiceRequestScreen(
                        service = service,
                        onBack = { navController.popBackStack() },
                        onSubmit = { navController.navigate(Routes.SERVICE_SUCCESS) }
                    )
                }

                composable(Routes.SERVICE_SUCCESS) {
                    ServiceSuccessScreen(
                        serviceName = "Spa & Piscina",
                        date = "2026-03-11",
                        time = "08:30",
                        onBack = {
                            navController.navigate(Routes.SERVICES) {
                                popUpTo(Routes.SERVICES) { inclusive = true }
                            }
                        }
                    )
                }

                // ── Guide ─────────────────────────────────────────────────────
                composable(Routes.GUIDE) {
                    GuideScreen(
                        onAttractionClick = { attraction ->
                            navController.navigate("attraction/${attraction.id}")
                        }
                    )
                }

                composable(
                    route     = Routes.ATTRACTION,
                    arguments = listOf(navArgument("attractionId") { type = NavType.IntType })
                ) { backStack ->
                    val attrId     = backStack.arguments?.getInt("attractionId") ?: return@composable
                    val attraction = sampleAttractions.first { it.id == attrId }
                    AttractionDetailScreen(
                        attraction = attraction,
                        onBack = { navController.popBackStack() }
                    )
                }

                // ── Profile ───────────────────────────────────────────────────
                composable(Routes.PROFILE) {
                    if (isLoggedIn) {
                        ProfileScreen(
                            userName = userName,
                            userEmail = userEmail,
                            onEditProfile = { navController.navigate(Routes.EDIT_PROFILE) },
                            onMyReservations = { navController.navigate(Routes.MY_RESERVATIONS) },
                            onMyRequests = { navController.navigate(Routes.MY_REQUESTS) },
                            onLogout = {
                                isLoggedIn = false
                                navController.navigate(Routes.AUTH) {
                                    popUpTo(Routes.HOME) { inclusive = true }
                                }
                            }
                        )
                    } else {
                        GuestProfileScreen(
                            onLogin = { navController.navigate(Routes.AUTH) }
                        )
                    }
                }

                composable(Routes.EDIT_PROFILE) {
                    EditProfileScreen(
                        initialName = userName,
                        initialEmail = userEmail,
                        onBack = { navController.popBackStack() },
                        onSave = { navController.popBackStack() }
                    )
                }

                composable(Routes.MY_RESERVATIONS) {
                    MyReservationsScreen(onBack = { navController.popBackStack() })
                }

                composable(Routes.MY_REQUESTS) {
                    MyRequestsScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}
