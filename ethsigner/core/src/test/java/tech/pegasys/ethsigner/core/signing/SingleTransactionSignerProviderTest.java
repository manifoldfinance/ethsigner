/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.ethsigner.core.signing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import tech.pegasys.signers.secp256k1.api.SingleSignerProvider;
import tech.pegasys.signers.secp256k1.api.Signer;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SingleSignerProviderTest {

  private Signer Signer;
  private SingleSignerProvider signerFactory;

  @BeforeEach
  void beforeEach() {
    Signer = mock(Signer.class);
    signerFactory = new SingleSignerProvider(Signer);
  }

  @Test
  void whenSignerIsNullFactoryCreationFails() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> new SingleSignerProvider(null));
  }

  @Test
  void whenSignerAddressIsNullFactoryAvailableAddressesShouldReturnEmptySet() {
    when(Signer.getAddress()).thenReturn(null);

    final Collection<String> addresses = signerFactory.availableAddresses();
    assertThat(addresses).isEmpty();
  }

  @Test
  void whenSignerAddressIsNullFactoryGetSignerShouldReturnEmpty() {
    when(Signer.getAddress()).thenReturn(null);

    final Optional<Signer> signer = signerFactory.getSigner("0x0");
    assertThat(signer).isEmpty();
  }

  @Test
  void whenGetSignerWithMatchingAccountShouldReturnSigner() {
    when(Signer.getAddress()).thenReturn("0x0");

    final Optional<Signer> signer = signerFactory.getSigner("0x0");
    assertThat(signer).isNotEmpty();
  }

  @Test
  void getSignerAddressIsCaseInsensitive() {
    when(Signer.getAddress()).thenReturn("0xa");

    assertThat(signerFactory.getSigner("0xa")).isNotEmpty();
    assertThat(signerFactory.getSigner("0xA")).isNotEmpty();
  }

  @Test
  void whenGetSignerWithNullAddressShouldReturnEmpty() {
    assertThat(signerFactory.getSigner(null)).isEmpty();
  }

  @Test
  void whenGetSignerWithDifferentSignerAccountShouldReturnEmpty() {
    when(Signer.getAddress()).thenReturn("0x0");

    final Optional<Signer> signer = signerFactory.getSigner("0x1");
    assertThat(signer).isEmpty();
  }

  @Test
  void whenGetAvailableAddressesShouldReturnSignerAddress() {
    when(Signer.getAddress()).thenReturn("0x0");

    final Collection<String> addresses = signerFactory.availableAddresses();
    assertThat(addresses).containsExactly("0x0");
  }
}
