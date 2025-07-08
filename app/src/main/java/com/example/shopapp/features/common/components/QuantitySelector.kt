package com.example.shopapp.features.common.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.example.shopapp.R
import com.example.shopapp.core.util.Constants
import com.example.shopapp.features.productDetail.presentation.event.ProductDetailUiEvent
import com.example.shopapp.features.productDetail.presentation.state.ProductDetailState
import com.example.shopapp.ui.theme.Dimens

@Composable
fun QuantitySelector(
    modifier: Modifier = Modifier,

    currentQuantity: Int,
    onQuantityChanged: (Int) -> Unit,
    minQuantity: Int = 0,
    maxQuantity: Int = Int.MAX_VALUE,


//    state: ProductDetailState,
//    event: (ProductDetailUiEvent) -> Unit,
) {

    val context = LocalContext.current

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.MediumCornerRadius))
//            .padding(bottom = Dimens.SmallPadding / 9) // Move it up by half its height to sit on the dividing line
            .background(colorResource(R.color.blue))
            .height(Dimens.MediumBoxHeight)
            .border(
                Dimens.SmallBorderWidth,
                colorResource(R.color.blue),
                RoundedCornerShape(Dimens.LargeCornerRadius)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.SmallPadding),
    ) {

        //Subtract
        IconButton(
            onClick = {

                if (currentQuantity > minQuantity) {
                    onQuantityChanged(currentQuantity - 1)
                } else {
                    Constants.showToast(context = context, message = "Can't be less than $minQuantity")
                }

               /* if(state.items!!.cartQuantity==0){
                    Constants.showToast(context = context, message = "Can't be less 0")
                }else{
                    event(ProductDetailUiEvent.setQuantity(state.items.cartQuantity - 1))
                }*/
            },
            modifier = Modifier
                .padding(Dimens.MediumPadding)
                .size(Dimens.IconSizeMedium)
                .clip(CircleShape) // Make it circular
                .background(
                    colorResource(R.color.white),
                    shape = CircleShape
                ),
            // Disable if at min quantity
            enabled = currentQuantity > minQuantity
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Subtract Quantity",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = currentQuantity.toString()/*state.items?.cartQuantity.toString()*/,
            modifier = Modifier
                .padding(Dimens.MediumPadding)
        )

        // Add Button
        IconButton(
            onClick = {
                if (currentQuantity < maxQuantity) {
                    onQuantityChanged(currentQuantity + 1)
                } else {
                    Constants.showToast(context = context, message = "Can't be more than $maxQuantity")
                }
//                event(ProductDetailUiEvent.setQuantity(state.items?.cartQuantity?.plus(1) ?: 0))
            },
            modifier = Modifier
                .padding(Dimens.MediumPadding)
                .size(Dimens.IconSizeMedium)
                .clip(CircleShape) // Make it circular
                .background(
                    colorResource(R.color.white),
                    shape = CircleShape
                ), // Example background
            // Disable if at max quantity
            enabled = currentQuantity < maxQuantity
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Quantity",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}