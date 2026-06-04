//package org.locker.tictactoe.presentation.compose.screens.game.view
//
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.ui.Modifier
//import org.jetbrains.compose.resources.stringResource
//import org.locker.tictactoe.presentation.common.TicTacToeIconButton
//import org.locker.tictactoe.presentation.model.BackButton
//import org.locker.tictactoe.presentation.theme.Size24
//import org.locker.tictactoe.presentation.theme.Size40
//import tictactoe.shared.generated.resources.Res
//import tictactoe.shared.generated.resources.ic_back
//import tictactoe.shared.generated.resources.ic_back_arrow_name
//
//fun gameBackButton(
//    onClick: () -> Unit,
//): BackButton = BackButton {
//    TicTacToeIconButton(
//        icon = Res.drawable.ic_back,
//        onClick = onClick,
//        contentColor = MaterialTheme.colorScheme.secondaryContainer,
//        contentDescription = stringResource(Res.string.ic_back_arrow_name),
//        iconModifier = Modifier.size(Size24),
//        modifier = Modifier.size(Size40)
//    )
//}
