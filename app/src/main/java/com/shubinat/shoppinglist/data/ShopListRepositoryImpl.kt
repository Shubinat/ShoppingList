package com.shubinat.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shubinat.shoppinglist.domain.ShopItem
import com.shubinat.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {
    private val shopItemsLD = MutableLiveData<List<ShopItem>>()
    private val shopItems = mutableListOf<ShopItem>()
    private var autoIncrementId = 0


    init {
        for (i in 0 until 10) {
            val shopItem = ShopItem("Item $i", i, true)
            addShopItem(shopItem)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopItems.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopItems.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldItem = getShopItem(shopItem.id)
        deleteShopItem(oldItem)
        addShopItem(shopItem)

    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopItems.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Shop item with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopItemsLD
    }

    private fun updateList() {
        shopItemsLD.value = shopItems.toList()
    }
}