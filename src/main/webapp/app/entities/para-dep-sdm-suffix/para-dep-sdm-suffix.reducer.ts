import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaDepSdmSuffix, defaultValue } from 'app/shared/model/para-dep-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARADEPS: 'paraDep/SEARCH_PARADEPS',
  FETCH_PARADEP_LIST: 'paraDep/FETCH_PARADEP_LIST',
  FETCH_PARADEP: 'paraDep/FETCH_PARADEP',
  CREATE_PARADEP: 'paraDep/CREATE_PARADEP',
  UPDATE_PARADEP: 'paraDep/UPDATE_PARADEP',
  DELETE_PARADEP: 'paraDep/DELETE_PARADEP',
  RESET: 'paraDep/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaDepSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaDepSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaDepSdmSuffixState = initialState, action): ParaDepSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARADEPS):
    case REQUEST(ACTION_TYPES.FETCH_PARADEP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARADEP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARADEP):
    case REQUEST(ACTION_TYPES.UPDATE_PARADEP):
    case REQUEST(ACTION_TYPES.DELETE_PARADEP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARADEPS):
    case FAILURE(ACTION_TYPES.FETCH_PARADEP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARADEP):
    case FAILURE(ACTION_TYPES.CREATE_PARADEP):
    case FAILURE(ACTION_TYPES.UPDATE_PARADEP):
    case FAILURE(ACTION_TYPES.DELETE_PARADEP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARADEPS):
    case SUCCESS(ACTION_TYPES.FETCH_PARADEP_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARADEP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARADEP):
    case SUCCESS(ACTION_TYPES.UPDATE_PARADEP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARADEP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-deps';
const apiSearchUrl = 'api/_search/para-deps';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaDepSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARADEPS,
  payload: axios.get<IParaDepSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaDepSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARADEP_LIST,
    payload: axios.get<IParaDepSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaDepSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARADEP,
    payload: axios.get<IParaDepSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaDepSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARADEP,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaDepSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARADEP,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaDepSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARADEP,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
