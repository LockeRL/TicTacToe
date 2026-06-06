package com.locker.core.navigation

import androidx.navigation3.runtime.NavBackStack
import com.locker.core.navigation.keys.BaseNavKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object NavBackStackSerializer : KSerializer<NavBackStack<BaseNavKey>> {
	private val delegateSerializer = ListSerializer(BaseNavKey.serializer())

	override val descriptor: SerialDescriptor = delegateSerializer.descriptor

	override fun serialize(encoder: Encoder, value: NavBackStack<BaseNavKey>) {
		encoder.encodeSerializableValue(delegateSerializer, value.toList())
	}

	override fun deserialize(decoder: Decoder): NavBackStack<BaseNavKey> {
		val list = decoder.decodeSerializableValue(delegateSerializer)
		return NavBackStack(*list.toTypedArray())
	}
}
