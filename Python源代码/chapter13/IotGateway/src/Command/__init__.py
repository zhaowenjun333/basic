

dicInt_Type={}
from . import AddAccount
dicInt_Type[AddAccount.CAddAccount.command_id]=AddAccount.CAddAccount

from . import AddApartment
dicInt_Type[AddApartment.CAddApartment.command_id]=AddApartment.CAddApartment

from . import AddDevice
dicInt_Type[AddDevice.CAddDevice.command_id]=AddDevice.CAddDevice

from . import AddRelayer
dicInt_Type[AddRelayer.CAddRelayer.command_id]=AddRelayer.CAddRelayer

from . import Authorize
dicInt_Type[Authorize.CAuthorize.command_id]=Authorize.CAuthorize

from . import BaseCommand
dicInt_Type[BaseCommand.CBaseCommand.command_id]=BaseCommand.CBaseCommand

from . import HeartBeat
dicInt_Type[HeartBeat.CHeartBeat.command_id]=HeartBeat.CHeartBeat

from . import ModifyApartment
dicInt_Type[ModifyApartment.CModifyApartment.command_id]=ModifyApartment.CModifyApartment

from . import ModifyRelayer
dicInt_Type[ModifyRelayer.CModifyRelayer.command_id]=ModifyRelayer.CModifyRelayer

from . import RemoveApartment
dicInt_Type[RemoveApartment.CRemoveApartment.command_id]=RemoveApartment.CRemoveApartment

from . import RemoveDevice
dicInt_Type[RemoveDevice.CRemoveDevice.command_id]=RemoveDevice.CRemoveDevice

from . import RemoveRelayer
dicInt_Type[RemoveRelayer.CRemoveRelayer.command_id]=RemoveRelayer.CRemoveRelayer

from . import ControlDevice
dicInt_Type[ControlDevice.CControlDevice.command_id]=ControlDevice.CControlDevice
dicInt_Type[ControlDevice.CControlDeviceResp.command_id]=ControlDevice.CControlDeviceResp

from . import QueryDevice
dicInt_Type[QueryDevice.CQueryDevice.command_id]=QueryDevice.CQueryDevice
dicInt_Type[QueryDevice.CQueryDeviceResp.command_id]=QueryDevice.CQueryDeviceResp

from . import QueryAccount
dicInt_Type[QueryAccount.CQueryAccount.command_id]=QueryAccount.CQueryAccount

from . import QueryApartment
dicInt_Type[QueryApartment.CQueryApartment.command_id]=QueryApartment.CQueryApartment

from . import SetArm
dicInt_Type[SetArm.CSetArm.command_id]=SetArm.CSetArm

from . import SetProfile
dicInt_Type[SetProfile.CSetProfile.command_id]=SetProfile.CSetProfile




